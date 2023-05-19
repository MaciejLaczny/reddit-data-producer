package com.api.pipeline;

import com.api.pipeline.model.ListingElement;
import com.api.pipeline.model.ArticleContent;
import com.api.pipeline.model.ListingElementContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class ListingConsumerTest {
    List<ListingElement> testLinks = List.of(
            ListingElement.builder()
                    .content(ArticleContent.builder().fullName("failure")
                    .ts(6666L).build())
                    .build(),

            ListingElement.builder()
                    .content(ArticleContent.builder().fullName("failure")
                    .ts(7777L).build())
                    .build(),

            ListingElement.builder()
                    .content(ArticleContent.builder().fullName("success")
                    .ts(8888L).build())
                    .build()
            );

    @Test
    void getsLatestTs() {
        String latestTimestamp = recentElement(testLinks);
        Assertions.assertEquals("success", latestTimestamp);
    }

    String recentElement(List<ListingElement> links) {
        return links.stream()
                .map(ListingElement::content)
                .max(Comparator.comparingLong(ListingElementContent::ts))
                .map(ListingElementContent::fullName)
                .orElse(null);
    }

}