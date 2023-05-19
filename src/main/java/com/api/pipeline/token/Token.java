package com.api.pipeline.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record Token(@JsonProperty("access_token") String accessToken){ }
