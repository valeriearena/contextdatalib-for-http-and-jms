package com.example.common.enums;

import lombok.Getter;

@Getter
public enum ContextDataFieldEnum {

    USER_NAME_HEADER("User-Name");

    private String name;

    private ContextDataFieldEnum(String name){
        this.name = name;
    }
}
