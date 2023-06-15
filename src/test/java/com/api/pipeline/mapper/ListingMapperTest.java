package com.api.pipeline.mapper;

import com.api.pipeline.model.ArticleCommentMessage;
import com.api.pipeline.model.ArticleContent;
import com.api.pipeline.model.CommentContent;
import com.api.pipeline.model.ListingElementContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class ListingMapperTest {

    @InjectMocks
    ListingMapper mapper;

    @Test
    void mapArticleWithComments() {
        //Given
        ListingElementContent article = ArticleContent.builder()
                .ts(9999L)
                .fullName("article1")
                .title("uniquetitle")
                .build();

        List<ListingElementContent> comments = List.of(
                CommentContent.builder()
                        .ts(9898L)
                        .fullname("comment1")
                        .body("commentbody1")
                        .build(),
                CommentContent.builder()
                        .ts(9998L)
                        .fullname("comment2")
                        .body("commentbody2")
                        .build(),
                CommentContent.builder()
                        .ts(9777L)
                        .fullname("comment3")
                        .body("commentbody3")
                        .build()
        );

        //When
        List<ArticleCommentMessage> messages = mapper.mapArticleWithComments(article, comments);

        //Then
        Assertions.assertTrue(messages.stream()
                .map(ArticleCommentMessage::comment)
                .toList()
                .containsAll(comments.stream().map(ListingElementContent::body).collect(Collectors.toSet())));

        messages.stream()
                .map(ArticleCommentMessage::articleTitle)
                .toList()
                .forEach(t -> Assertions.assertEquals(t, article.body()));
    }

    @Test
    void mapArticleWithCommentsEmptyComments() {
        //Given
        ListingElementContent article = ArticleContent.builder()
                .ts(9999L)
                .fullName("article1")
                .title("uniquetitle")
                .build();

        List<ListingElementContent> commentsEmpty = Collections.emptyList();

        //When
        List<ArticleCommentMessage> messages = mapper.mapArticleWithComments(article, commentsEmpty);

        //Then
        Assertions.assertTrue(messages.isEmpty());
    }
}