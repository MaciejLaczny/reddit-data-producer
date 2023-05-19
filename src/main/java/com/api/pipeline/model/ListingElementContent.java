package com.api.pipeline.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use= JsonTypeInfo.Id.DEDUCTION, defaultImpl = ArticleContent.class)
@JsonSubTypes({@JsonSubTypes.Type(ArticleContent.class), @JsonSubTypes.Type(CommentContent.class)})
public interface ListingElementContent {
    String fullName();
    Long ts();
    String body();
}
