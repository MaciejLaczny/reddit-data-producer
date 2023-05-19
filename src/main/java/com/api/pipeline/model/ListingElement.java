package com.api.pipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ListingElement(@JsonProperty("data") ListingElementContent content) {
}
