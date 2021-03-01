package com.example.common.aop;

import brave.Tracer;
import com.example.common.context.ContextData;
import com.example.common.context.ContextService;
import com.example.common.context.ExampleContextData;
import com.example.common.enums.ContextDataFieldEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.Message;
import java.util.StringJoiner;

/**
 * 1. Sending JMS message via MessageConverter::toMessage -
 *      When JMS publishers send JMS messages via JmsTemplate::convertAndSend(String destinationName, final Object message),
 *      JmsTemplate makes calls to implementations of MessageConverter::toMessage to convert the Object to a standard JMS.
 *      By default, Spring uses SimpleMessageConverter.
 *
 * 2. Receiving the JMS message via the method annotated with @JmsListener in JMS subscribers.
 *
 */
@Slf4j
@Aspect
@Service
public class JmsAspect{

    private final ContextData contextData;
    private final ContextService contextService;
    private final JmsTemplate jmsTemplate;
    private final MessageConverter messageConverter;

    public JmsAspect(
            final ContextData contextData,
            final ContextService contextService,
            final JmsTemplate jmsTemplate,
            final MessageConverter messageConverter) {
        this.contextData = contextData;
        this.contextService = contextService;
        this.jmsTemplate = jmsTemplate;
        this.messageConverter = messageConverter;
    }

    /**
     * Sending JMS message - Intercept the call to MessageConverter::toMessage.
     * Intercept MessageConverter::toMessage AFTER RETURNING to access the javax.jms.Message and
     *  add a custom 'Authorization' property for the JWT token.
     *
     * NOTE:
     * MessageConverter is the interface that all Spring message converters implement.
     * This pointcut should work for all message converters. By default, Spring uses SimpleMessageConverter.
     */
    @AfterReturning(pointcut="execution(public javax.jms.Message org.springframework.jms.support.converter.MessageConverter.toMessage(Object, javax.jms.Session) "+
            "throws javax.jms.JMSException, org.springframework.jms.support.converter.MessageConversionException)", returning="retVal")
    public void addContextDataToJmsProducer(Object retVal)throws Exception{

        log.info("Entering JmsAspect After JMS Publisher...");
        Message message = (Message)retVal;
        message.setStringProperty(ContextDataFieldEnum.USER_NAME_HEADER.getName(), contextData.getUserName());
    }

    /**
     * Receiving JMS message for processing - Intercept the method annotated with @JmsListener.
     * Intercept the method BEFORE processing the JMS message and read the custom JMS 'Authorization' and 'transaction_id' properties:
     * 1. Authenticate the JWT token for the thread that receives and process the JMS message.
     * 2. Add the context data and bind it to the thread.
     * 3. Initialize the connection to the tenant database for the thread.
     *
     * NOTE: Around advice can perform custom behavior before and after the method invocation.
     * Method invocation is done explicity using ProceedingJoinPoint::proceed();
     * We are using it here to ensure that if an exception is thrown, the JMS will not be processed.
     */
    @Around("@annotation(org.springframework.jms.annotation.JmsListener)")
    public void addContextDataToJmsConsumer(ProceedingJoinPoint joinPoint) throws Exception{
        try{
            log.info("Entering JmsAspect Before JMS Listener...");
            Object[] signatureArgs = joinPoint.getArgs();
            Message message = (Message)signatureArgs[0];

            String userName = message.getStringProperty(ContextDataFieldEnum.USER_NAME_HEADER.getName());
            ExampleContextData exampleContextData = ExampleContextData.builder().userName(userName).build();
            contextService.buildContextData(exampleContextData);

            joinPoint.proceed();
        }
        catch (Throwable e){
            log.error(String.format("Failure when intercepting JMS message. Message will not be processed. error={}", e.getMessage()), e);
        }

    }

    /**
     * Receiving JMS message for processing - Intercept the method annotated with @JmsListener.
     * Intercept the method AFTER processing the JMS message to remove the context data when the thread exits.
     * This will execute when the JMS message is processed successfully or if an exception is thrown.
     */
    @After("@annotation(org.springframework.jms.annotation.JmsListener)")
    public void removeContextDataFromJmsConsumer(){
        log.info("Entering JmsAspect After JMS Listener...");
        contextService.removeContextData();
    }

    /**
     * Spring Aspects can only intercept Spring beans.
     * When JmsTemplate is created, the MessageConverter configured in JmsTemplate is not registered as a Spring bean.
     * Configure JmsTemplate with the Spring managed MessageConverter created in JmsConfig.
     */
    @PostConstruct
    public void init(){
        jmsTemplate.setMessageConverter(messageConverter);
    }

}