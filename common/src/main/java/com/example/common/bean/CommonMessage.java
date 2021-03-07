package com.example.common.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonMessage {
    private String userName;
    private String message;
}
