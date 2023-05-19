package com.api.pipeline.producer;

import com.api.pipeline.model.ArticleCommentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService implements ProducerService {
    private final KafkaTemplate<String, ArticleCommentMessage> kafkaTemplate;
    private final String topic;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, ArticleCommentMessage> kafkaTemplate,
                                @Value("${spring.kafka.producer.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void send(ArticleCommentMessage msg) {
        log.info("sending articleTitle = {} ", msg.articleTitle());
        log.info("sending comment = {} ", msg.comment());
        kafkaTemplate.send(topic, msg.articleId(), msg);
    }
}
