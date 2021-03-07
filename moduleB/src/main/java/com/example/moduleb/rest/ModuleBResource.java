package com.example.moduleb.rest;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextService;
import com.example.moduleb.service.ModuleBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/moduleb")
public class ModuleBResource {

    private final ContextService contextService;
    private final ModuleBService moduleBService;

    public ModuleBResource(final ContextService contextService, final ModuleBService moduleBService) {
        this.contextService = contextService;
        this.moduleBService = moduleBService;
    }

    @GetMapping
    public CommonMessage getExample(){
        log.info("Module B received request from {}.", contextService.getUserName());
        return moduleBService.getWebResponse();
    }
}
