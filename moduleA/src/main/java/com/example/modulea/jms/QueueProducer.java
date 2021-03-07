package com.example.modulea.jms;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueueProducer {

    private final ContextData contextData;
    private final JmsTemplate jmsTemplate;

    @Value("${modulea.jms.queue.name}")
    private String queueName;

    public QueueProducer(final ContextData contextData, final JmsTemplate jmsTemplate) {
        this.contextData = contextData;
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(){
        log.info("Module A sending JMS message. userName={}", contextData.getUserName());
        CommonMessage commonMessage =
                CommonMessage.builder()
                        .userName(contextData.getUserName())
                        .message(String.format("Hello! This is a JMS message from %s!", contextData.getUserName()))
                        .build();
        String jsonMessage = new Gson().toJson(commonMessage);
        jmsTemplate.convertAndSend(queueName, jsonMessage);
    }
}
