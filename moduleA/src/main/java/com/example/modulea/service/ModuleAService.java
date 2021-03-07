package com.example.modulea.service;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextData;
import com.example.common.rest.webclient.CommonWebClient;
import com.example.modulea.jms.QueueProducer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ModuleAService {

    @Value("${moduleb.uri}")
    private String modulebUri;

    private final ContextData contextData;
    private final QueueProducer queueProducer;
    private final CommonWebClient commonWebClient;

    public ModuleAService(final ContextData contextData, final QueueProducer queueProducer, final CommonWebClient commonWebClient) {
        this.contextData = contextData;
        this.queueProducer = queueProducer;
        this.commonWebClient = commonWebClient;
    }

    public CommonMessage makeWebRequest(){
        //queueProducer.sendMessage();
        return sendWebRequest();
    }

    private CommonMessage sendWebRequest(){
        log.info("Module A sending HTTP request. userName={}", contextData.getUserName());
        String jsonResponse = commonWebClient.makeGetRequest(modulebUri);
        return new Gson().fromJson(jsonResponse, CommonMessage.class);
    }
}
