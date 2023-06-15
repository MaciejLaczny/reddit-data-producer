package com.api.pipeline;

import com.api.pipeline.model.ArticleContent;
import com.api.pipeline.model.ListingElementContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ListingConsumerTest {
    @InjectMocks
    ElementExtractor extractor;
    List<ListingElementContent> testLinks = List.of(
            ArticleContent.builder().fullName("failure")
                    .ts(9999L).build(),

            ArticleContent.builder().fullName("failure")
                    .ts(8888L).build(),

            ArticleContent.builder().fullName("success")
                    .ts(6666L).build()
            );

    @Test
    void getEarliest() {
        ListingElementContent earliest = extractor.earliest(testLinks);
        Assertions.assertEquals(6666L, earliest.ts());
    }

}