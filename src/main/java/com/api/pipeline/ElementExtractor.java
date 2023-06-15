package com.api.pipeline;

import com.api.pipeline.model.ListingElementContent;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ElementExtractor {
    public ListingElementContent earliest(List<ListingElementContent> elements) {
        return elements.stream()
                .filter(e -> Objects.nonNull(e.fullName()))
                .min(Comparator.comparingLong(ListingElementContent::ts))
                .orElse(null);
    }
}

