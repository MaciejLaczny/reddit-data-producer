package com.api.pipeline.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper mapper(){
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean("loggingRestTemplateBuilder")
    RestTemplateBuilder loggingRestTemplateBuilder(){
        return new RestTemplateBuilder()
                .requestFactory(() -> {
                    HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
                    return new BufferingClientHttpRequestFactory(rf);
                })
                .interceptors(new LoggingInterceptor());
    }

}
