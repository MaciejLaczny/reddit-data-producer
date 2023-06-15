package com.api.pipeline.apiConsumer;

import com.api.pipeline.mapper.ListingMapper;
import com.api.pipeline.model.Listing;
import com.api.pipeline.model.ListingContent;
import com.api.pipeline.model.ListingElement;
import com.api.pipeline.model.ListingElementContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ListingConsumer {
    private final RestTemplate restTemplate;
    private final ListingMapper mapper;

    @Autowired
    public ListingConsumer(@Qualifier("loggingRestTemplateBuilder") RestTemplateBuilder builder, ListingMapper mapper) {
        this.restTemplate = builder.build();
        this.mapper = mapper;
    }
    public List<ListingElementContent> fetchListing(RedditApiRequest rq) {
        return Optional.of(getListingResponse(rq))
                .map(mapper::mapToListing)
                .map(Listing::content)
                .map(ListingContent::listingElements)
                .map(c -> c.stream()
                        .map(ListingElement::content)
                        .toList())
                .orElse(Collections.emptyList());
    }
    private ResponseEntity<String> getListingResponse(RedditApiRequest listing) {
        HttpEntity<String> entity = new HttpEntity<>(listing.headers());
        URI uri = listing.uri();
        return restTemplate.exchange(uri, HttpMethod.GET, entity , String.class);
    }
}