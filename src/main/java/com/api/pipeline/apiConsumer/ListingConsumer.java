package com.api.pipeline.apiConsumer;

import com.api.pipeline.model.Listing;
import com.api.pipeline.mapper.ListingMapper;
import com.api.pipeline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;
@Service
public class ListingConsumer {
    private final RestTemplate restTemplate;
    private final ListingMapper mapper;

    @Autowired
    public ListingConsumer(@Qualifier("loggingRestTemplateBuilder") RestTemplateBuilder builder, ListingMapper mapper) {
        this.restTemplate = builder.build();
        this.mapper = mapper;
    }
    public List<ListingElement> fetchListing(RedditApiRequest rq) {
        return Optional.of(getListingResponse(rq))
                .map(mapper::mapToListing)
                .map(Listing::content)
                .map(ListingContent::listingElements)
                .orElse(Collections.emptyList());
    }
    private ResponseEntity<String> getListingResponse(RedditApiRequest listing) {
        HttpEntity<String> entity = new HttpEntity<>(listing.headers());
        URI uri = listing.uri();
        return restTemplate.exchange(uri, HttpMethod.GET, entity , String.class);
    }
}