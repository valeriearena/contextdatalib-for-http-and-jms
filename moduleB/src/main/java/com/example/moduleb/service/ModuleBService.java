package com.example.moduleb.service;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ModuleBService {

    private final ContextData contextData;

    public ModuleBService(final ContextData contextData) {
        this.contextData = contextData;
    }

    public CommonMessage getWebResponse() {
        log.info("Module B returning HTTP response. userName={}", contextData.getUserName());
        return CommonMessage.builder()
                .userName(contextData.getUserName())
                .message(String.format("Hello! This is an HTTP response for %s!", contextData.getUserName()))
                .build();
    }
}
