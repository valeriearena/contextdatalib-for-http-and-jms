package com.example.common.rest.webclient;

import com.example.common.context.ContextData;
import com.example.common.enums.ContextDataFieldEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class CommonWebClient {

    private final ContextData contextData;
    private final WebClient webClient;

    public CommonWebClient(final ContextData contextData, final WebClient webClient) {
        this.contextData = contextData;
        this.webClient = webClient;
    }

    public String makeGetRequest(String uri) {
        log.info("Inside CommonWebClient. userName={}", contextData.getUserName());
        String jsonResponse =
                webClient
                        .get()
                        .uri(uri)
                        .header(ContextDataFieldEnum.USER_NAME_HEADER.getName(), contextData.getUserName())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        return jsonResponse;
    }

}
