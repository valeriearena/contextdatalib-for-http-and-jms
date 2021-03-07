package com.example.modulea.service;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextService;
import com.example.common.rest.webclient.CommonWebClient;
import com.example.modulea.jms.JmsTopicPublisher;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ModuleAService {

    @Value("${moduleb.uri}")
    private String modulebUri;

    private final ContextService contextService;
    private final JmsTopicPublisher jmsTopicPublisher;
    private final CommonWebClient commonWebClient;

    public ModuleAService(final ContextService contextService, final JmsTopicPublisher jmsTopicPublisher, final CommonWebClient commonWebClient) {
        this.contextService = contextService;
        this.jmsTopicPublisher = jmsTopicPublisher;
        this.commonWebClient = commonWebClient;
    }

    public CommonMessage sendMessage(){
        jmsTopicPublisher.sendJmsMessage();
        return sendWebRequest();
    }

    private CommonMessage sendWebRequest(){
        log.info("Module A sending HTTP request. userName={}", contextService.getUserName());
        String jsonResponse = commonWebClient.makeGetRequest(modulebUri);
        return new Gson().fromJson(jsonResponse, CommonMessage.class);
    }
}
