package com.api.pipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.api.pipeline.model.ListingContent;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record Listing (@JsonProperty("data") ListingContent content){
}
