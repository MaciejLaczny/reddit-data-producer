package com.api.pipeline.apiConsumer;

import com.api.pipeline.config.RedditConsumerConfig;
import com.api.pipeline.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class RedditRequestFactory {
    private final RedditConsumerConfig config;
    private final TokenService tokenService;

    public RedditApiRequest createRequest(URI uri) {
        HttpHeaders headers = config.headers();
        headers.add("Authorization", "bearer " + tokenService.currentToken().accessToken());
        return RedditApiRequest.builder()
                .headers(headers)
                .uri(uri)
                .build();
    }
}
