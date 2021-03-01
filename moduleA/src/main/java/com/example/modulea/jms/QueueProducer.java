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

    public void produceMessage(CommonMessage commonMessage){
        log.info("Inside QueueProducer. userName={}", contextData.getUserName());
        String jsonMessage = new Gson().toJson(commonMessage);
        jmsTemplate.convertAndSend(queueName, jsonMessage);
    }
}
