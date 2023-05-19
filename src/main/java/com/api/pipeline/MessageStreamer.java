package com.api.pipeline;

import com.api.pipeline.apiConsumer.*;
import com.api.pipeline.config.RedditConsumerConfig;
import com.api.pipeline.mapper.ListingMapper;
import com.api.pipeline.model.ArticleCommentMessage;
import com.api.pipeline.producer.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageStreamer {
    private final KafkaProducerService kafkaProducer;
    private final CommentConsumer consumer;
    @Scheduled(fixedRate = 60000)
    void streamRedditToKafka() {
        var articleCommentMessages = consumer.fetchComments();
        articleCommentMessages.forEach(kafkaProducer::send);
    }
}


