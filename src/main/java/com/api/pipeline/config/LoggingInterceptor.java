package com.api.pipeline.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("URI: {}", request.getURI());
        log.info("request body: {}", getRequestBody(body));
        log.info("HTTP Method: {}", request.getMethodValue());
        log.info("Headers: {}", request.getHeaders());
        ClientHttpResponse rs = execution.execute(request, body);
        String rsBody = getResponseBody(rs);
        log.info("response body: {}", rsBody);
        return rs;
    }

    private String getResponseBody(ClientHttpResponse rs) {
            if(rs != null) {
                try {
                    return new String(rs.getBody().readAllBytes());
                } catch (IOException e) { /* ignore */ }
            }
            return "empty";
    }

    private String getRequestBody(byte[] body) {
       return new String(body);
    }
}
