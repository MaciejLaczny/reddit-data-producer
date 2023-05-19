package com.api.pipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ArticleContent(@JsonProperty("created_utc") Long ts,
                             @JsonProperty("name") String fullName,
                             @JsonProperty("title") String title) implements ListingElementContent{

    @Override
    public String fullName() {
        return fullName;
    }

    @Override
    public Long ts() {
        return ts;
    }

    @Override
    public String body() {
        return title;
    }
}
