package com.example.moduleA.rest;

import com.example.common.context.ContextData;
import com.example.moduleA.service.ModuleAService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modulea")
public class ModuleAResource {

    private final ContextData contextData;
    private final ModuleAService moduleAService;

    public ModuleAResource(final ContextData contextData, final ModuleAService moduleAService) {
        this.contextData = contextData;
        this.moduleAService = moduleAService;
    }

    @GetMapping
    public String getExample(){
        return "hello";
    }
}
