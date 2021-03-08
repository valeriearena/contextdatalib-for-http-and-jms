package com.moduleb.jms;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Slf4j
@Service
public class JmsTopicSubscriber {

    private final ContextData contextData;

    public JmsTopicSubscriber(final ContextData contextData) {
        this.contextData = contextData;
    }

    @JmsListener(
            destination = "${moduleb.jms.topic.name}",
            subscription = "${moduleb.jms.topic.subscription}",
            containerFactory = "${moduleb.jms.topic.connection.factory}")
    public void receiveMessage(TextMessage message){
        try {
            String jsonMessage = message.getText();
            CommonMessage commonMessage = new Gson().fromJson(jsonMessage, CommonMessage.class);
            log.info("Module B consuming JMS message. userName={}, message={}", contextData.getUserName(), commonMessage);
        }
        catch (JMSException e){
            log.error(e.getMessage(), e);
        }
    }
}
