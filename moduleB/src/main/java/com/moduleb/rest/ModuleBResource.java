package com.moduleb.rest;

import com.example.common.bean.CommonMessage;
import com.example.common.context.ContextData;
import com.moduleb.service.ModuleBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/moduleb/examples")
public class ModuleBResource {

    private final ContextData contextData;
    private final ModuleBService moduleBService;

    public ModuleBResource(final ContextData contextData, final ModuleBService moduleBService) {
        this.contextData = contextData;
        this.moduleBService = moduleBService;
    }

    @GetMapping
    public CommonMessage getExample(){
        log.info("Module B receiving REST request. userName={}, jwt={}", contextData.getUserName(), contextData.getJwtToken());
        return moduleBService.getWebResponse();
    }
}
