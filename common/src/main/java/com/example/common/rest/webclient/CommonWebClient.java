package com.example.common.rest.webclient;

import com.example.common.context.ContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class CommonWebClient {

    private final ContextService contextService;
    private final WebClient webClient;

    public CommonWebClient(final ContextService contextService, final WebClient webClient) {
        this.contextService = contextService;
        this.webClient = webClient;
    }

    public String makeGetRequest(String uri) {

        log.info("Sending web request with context data. concontextData={}", contextService.getContextData());

        String bearerToken = contextService.getBearerToken();

        String jsonResponse =
                webClient
                        .get()
                        .uri(uri)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        return jsonResponse;
    }

}
