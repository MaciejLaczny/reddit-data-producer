package com.api.pipeline.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.api.pipeline.config.RedditConsumerConfig;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenService {
    @Autowired
    RedditConsumerConfig config;
    @Autowired
    ObjectMapper mapper;
    RestTemplate restTemplate;
    Stopwatch stopwatch;
    private Token currentToken;

    @Autowired
    public TokenService(@Qualifier("loggingRestTemplateBuilder") RestTemplateBuilder builder) {
        this.restTemplate = builder
                .basicAuthentication("ImCmUsWs5MyEZosqYTZRGg", "IlfJlvpkT_Kcb3RH9fG8iLKIj_AdDQ")
                .build();
    }

    @PostConstruct
    void firstCall(){
        this.stopwatch = Stopwatch.createStarted();
        this.currentToken = readToken(fetchToken().getBody());
    }

    public Token currentToken() {
        if (stopwatch.elapsed(TimeUnit.HOURS) > 12L) {
            this.stopwatch = Stopwatch.createStarted();
            this.currentToken = readToken(fetchToken().getBody());
            return currentToken;
        }
        return this.currentToken;
    }

    private ResponseEntity<String> fetchToken() {
        HttpHeaders headers = getAccessTokenHeaders();
        TokenRequestBody body = buildTokenRequestBody();
        HttpEntity<String> entity = new HttpEntity<>(body.toUrlFormEncoded(), headers);
        return restTemplate.postForEntity(config.getTokenUrl(), entity, String.class);
    }

    private TokenRequestBody buildTokenRequestBody() {
        return TokenRequestBody.builder()
                .grant_type(config.getGrantType())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();
    }

    private Token readToken(String rs) {
        Token token = null;
        try {
            token = mapper
                    .readerFor(Token.class)
                    .readValue(rs);
        } catch (JsonProcessingException e) {
            log.error("cant parse json, access token missing");
        }
        return token;
    }

    private HttpHeaders getAccessTokenHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", config.getUserAgent());
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return httpHeaders;
    }
}
