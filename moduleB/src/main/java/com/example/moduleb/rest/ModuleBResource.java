package com.example.moduleb.rest;

import com.example.common.context.ContextData;
import com.example.moduleb.service.ModuleBService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moduleb")
public class ModuleBResource {

    private final ContextData contextData;
    private final ModuleBService moduleBService;

    public ModuleBResource(final ContextData contextData, final ModuleBService moduleBService) {
        this.contextData = contextData;
        this.moduleBService = moduleBService;
    }

    @GetMapping
    public String getExample(){
        return "hello";
    }
}
