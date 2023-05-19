package com.api.pipeline.config;

import com.api.pipeline.token.Token;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class RedditConsumerConfig {
    private String password;
    private String username;
    private String appId;
    private String secret;
    private String userAgent;
    private String listingUrl;
    private String commentsUrl;
    private String tokenUrl;
    private String grantType;

    public HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", userAgent);
        return headers;
    }

    public URI articleUri(String link) {
        return UriComponentsBuilder
                .fromUriString(listingUrl)
                .queryParam("before", link)
                .queryParam("raw_json", 1)
                .queryParam("limit", 10)
                .build()
                .toUri();
    }

    public URI commentsUri(String link){
        return UriComponentsBuilder
                .fromUriString(commentsUrl + link.substring(3))
                .queryParam("limit", 100)
                .queryParam("depth", 1)
                .build()
                .toUri();
    }
}