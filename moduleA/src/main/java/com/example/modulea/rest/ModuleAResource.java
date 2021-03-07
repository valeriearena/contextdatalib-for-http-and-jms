package com.example.modulea.rest;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextService;
import com.example.modulea.service.ModuleAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/modulea")
public class ModuleAResource {

    private final ContextService contextService;
    private final ModuleAService moduleAService;

    public ModuleAResource(final ContextService contextService, final ModuleAService moduleAService) {
        this.contextService = contextService;
        this.moduleAService = moduleAService;
    }

    @GetMapping
    public CommonMessage getExample(){
        log.info("Module A received HTTP request. userName={}", contextService.getUserName());
        return moduleAService.sendMessage();
    }
}
