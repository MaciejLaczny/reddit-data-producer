package com.api.pipeline.producer;

import com.api.pipeline.model.ArticleCommentMessage;

public interface ProducerService {
    void send(ArticleCommentMessage msg);
}