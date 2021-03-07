package com.example.common.rest.webclient;

import com.example.common.context.ContextService;
import com.example.common.enums.ContextDataFieldEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.StringJoiner;

@Slf4j
@Service
public class CommonWebClient {

    private final ContextService ContextService;
    private final WebClient webClient;

    public CommonWebClient(final ContextService ContextService, final WebClient webClient) {
        this.ContextService = ContextService;
        this.webClient = webClient;
    }

    public String makeGetRequest(String uri) {
        log.info("Inside CommonWebClient. userName={}", ContextService.getUserName());

        StringJoiner stringJoiner =
                new StringJoiner(" ").add(ContextDataFieldEnum.BEARER.getName()).add(ContextService.getJwtToken());

        String jsonResponse =
                webClient
                        .get()
                        .uri(uri)
                        .header(HttpHeaders.AUTHORIZATION, stringJoiner.toString())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        return jsonResponse;
    }

}
