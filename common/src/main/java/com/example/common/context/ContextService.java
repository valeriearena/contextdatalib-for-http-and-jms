package com.example.common.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContextService {

    private final ContextData contextData;

    public ContextService(final ContextData contextData) {
        this.contextData = contextData;
    }

    public void buildContextData(ExampleContextData exampleContextData){
        contextData.set(exampleContextData);
    }

    public void removeContextData() {
        contextData.remove();
    }
}