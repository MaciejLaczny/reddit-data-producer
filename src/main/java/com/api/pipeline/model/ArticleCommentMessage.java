package com.api.pipeline.model;

import lombok.Builder;

@Builder
public record ArticleCommentMessage(String articleId, String articleTitle, String comment) {
    @Override
    public String toString() {
        return "{" +
                "articleId='" + articleId + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
