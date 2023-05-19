package com.api.pipeline.apiConsumer;

import lombok.Builder;
import org.springframework.http.HttpHeaders;

import java.net.URI;

@Builder
public record RedditApiRequest(HttpHeaders headers, URI uri){ }
