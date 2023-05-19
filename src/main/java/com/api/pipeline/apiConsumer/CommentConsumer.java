package com.api.pipeline.apiConsumer;

import com.api.pipeline.ElementExtractor;
import com.api.pipeline.config.RedditConsumerConfig;
import com.api.pipeline.mapper.ListingMapper;
import com.api.pipeline.model.ArticleCommentMessage;
import com.api.pipeline.model.ListingElementContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentConsumer {
    private final ListingConsumer listingConsumer;
    private final ListingMapper mapper;
    private final RedditRequestFactory requestFactory;
    private final RedditConsumerConfig config;
    private final ElementExtractor extractor;
    private final ArticleConsumer articleConsumer;
    private String recentArticle = null;
    public List<ArticleCommentMessage> fetchComments(){
        List<ListingElementContent> articles = articleConsumer.fetchArticles(recentArticle);
        var eldestArticle = extractor.eldest(articles);
        this.recentArticle = eldestArticle.fullName();
        var commentsRequest = requestFactory.createRequest(config.commentsUri(recentArticle));
        var comments = listingConsumer.fetchListing(commentsRequest);
        return mapper.mapArticleWithComments(eldestArticle, comments);
    }
}
