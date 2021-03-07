package com.example.common.context;

import com.example.common.enums.ContextDataFieldEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@Slf4j
@Service
public class ContextService {

    private final ContextData contextData;

    public ContextService(final ContextData contextData) {
        this.contextData = contextData;
    }

    public void addContextData(ExampleContextData exampleContextData){
        contextData.set(exampleContextData);
    }

    public void removeContextData() {
        contextData.remove();
    }

    public String getBearerToken(){
        String jwt = contextData.getJwtToken();
        StringJoiner stringJoiner = new StringJoiner(" ").add(ContextDataFieldEnum.BEARER.getName()).add(jwt);
        return stringJoiner.toString();
    }

    public String getJwtToken() {
        return contextData.getJwtToken();
    }

    public String getUserName(){
        return contextData.getUserName();
    }

}