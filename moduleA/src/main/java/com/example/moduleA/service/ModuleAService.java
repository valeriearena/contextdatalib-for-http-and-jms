package com.example.moduleA.service;

import com.example.common.context.ContextData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ModuleAService {

    private final ContextData contextData;

    public ModuleAService(final ContextData contextData) {
        this.contextData = contextData;
    }

}
