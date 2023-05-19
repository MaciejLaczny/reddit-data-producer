package com.api.pipeline.config;

import com.api.pipeline.model.ArticleCommentMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {
    @Bean
    public KafkaTemplate<String, ArticleCommentMessage> kafkaTemplate(ProducerFactory<String, ArticleCommentMessage> pf) {
        return new KafkaTemplate<>(pf);
    }
}
