package com.api.pipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
public record ListingContent(@JsonProperty("children") List<ListingElement> listingElements) {
}
