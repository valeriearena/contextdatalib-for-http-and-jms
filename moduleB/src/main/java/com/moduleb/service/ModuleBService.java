package com.moduleb.service;

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
        log.info("Module B returning REST response. userName={}, jwt={}", contextData.getUserName(), contextData.getJwtToken());
        return CommonMessage.builder()
                .userName(contextData.getUserName())
                .message(String.format("Hello! This is an REST response for %s!", contextData.getUserName()))
                .build();
    }
}
