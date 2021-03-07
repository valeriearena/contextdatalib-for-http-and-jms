package com.example.moduleb.service;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ModuleBService {

    private final ContextService contextService;

    public ModuleBService(final ContextService contextService) {
        this.contextService = contextService;
    }

    public CommonMessage getWebResponse() {
        log.info("Module B returning HTTP response. userName={}", contextService.getUserName());
        return CommonMessage.builder()
                .userName(contextService.getUserName())
                .message(String.format("Hello! This is an HTTP response for %s!", contextService.getUserName()))
                .build();
    }
}
