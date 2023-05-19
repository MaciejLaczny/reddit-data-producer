package com.api.pipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CommentContent (@JsonProperty("body") String body,
                              @JsonProperty("created_utc") Long ts,
                              @JsonProperty("fullname") String fullname) implements ListingElementContent{

    @Override
    public String fullName() {
        return fullname;
    }
    @Override
    public Long ts() {
        return ts;
    }

}
