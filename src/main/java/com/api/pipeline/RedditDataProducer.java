package com.api.pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedditDataProducer {
	public static void main(String[] args) {
		SpringApplication.run(RedditDataProducer.class, args);
	}
}
