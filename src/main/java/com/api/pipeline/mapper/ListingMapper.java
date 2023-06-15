package com.api.pipeline.mapper;

import com.api.pipeline.model.ArticleCommentMessage;
import com.api.pipeline.model.Listing;
import com.api.pipeline.model.ListingElementContent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListingMapper {
    private final ObjectMapper mapper;
    public Listing mapToListing(ResponseEntity<String> rs) {
        String body = rs.getBody();
        if(isArray(body)) {
            return mapToCommentsListing(body);
        } else {
            return mapToLinksListing(body);
        }
    }
    public List<ArticleCommentMessage> mapArticleWithComments(ListingElementContent article, List<ListingElementContent> comments) {
        return comments.stream()
                .map(e -> ArticleCommentMessage.builder()
                        .articleId(article.fullName())
                        .articleTitle(article.body())
                        .comment(e.body())
                        .build())
                .toList();
    }

    private Listing mapToCommentsListing(String rs) {
        return uncheckJsonException(readArrayListing(rs));
    }

    private Listing mapToLinksListing(String rs) {
        return uncheckJsonException(readSingleListing(rs));
    }

    private Callable<Listing> readArrayListing(String rs) {
        return () -> {
            Listing[] listing = mapper.readerFor(Listing[].class).readValue(rs);
            return listing[1];
        };
    }

    private Callable<Listing> readSingleListing(String rs) {
        return () -> mapper.readerFor(Listing.class).readValue(rs);
    }

    private Listing uncheckJsonException(Callable<Listing> fn) {
        try {
            return fn.call();
        } catch (Exception e) {
            log.info(e.getMessage());
            return Listing.builder().build();
        }
    }

    private static Boolean isArray(String body) {
        return Optional.ofNullable(body)
                .map(s -> s.startsWith("["))
                .orElse(false);
    }
}
