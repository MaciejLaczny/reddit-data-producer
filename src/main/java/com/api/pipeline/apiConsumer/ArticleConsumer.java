package com.api.pipeline.apiConsumer;

import com.api.pipeline.config.RedditConsumerConfig;
import com.api.pipeline.mapper.ListingMapper;
import com.api.pipeline.model.ListingElementContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleConsumer {
    private final ListingConsumer listingConsumer;
    private final ListingMapper mapper;
    private final RedditRequestFactory requestFactory;
    private final RedditConsumerConfig config;

    List<ListingElementContent> fetchArticles(String recent) {
        var articleRequest = requestFactory.createRequest(config.articleUri(recent));
        return listingConsumer.fetchListing(articleRequest);
    }
}
