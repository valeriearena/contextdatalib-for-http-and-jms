package com.example.common.enums;

import lombok.Getter;

@Getter
public enum ContextDataFieldEnum {

    AUTHORIZATION("Authorization"),
    BEARER("Bearer");

    private String name;

    private ContextDataFieldEnum(String name){
        this.name = name;
    }
}
