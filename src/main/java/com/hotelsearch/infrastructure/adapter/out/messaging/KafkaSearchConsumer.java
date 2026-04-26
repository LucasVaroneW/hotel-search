package com.hotelsearch.infrastructure.adapter.out.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelsearch.domain.model.HotelSearch;
import com.hotelsearch.domain.port.out.SearchRepositoryPort;

@Component
public class KafkaSearchConsumer {

    private final SearchRepositoryPort searchRepository;
    private final ObjectMapper objectMapper;

    public KafkaSearchConsumer(SearchRepositoryPort searchRepository, ObjectMapper objectMapper) {
        this.searchRepository = searchRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "hotel_availability_searches", groupId = "hotel_search_group")
    public void consumeSearchEvent(String message) {
        try {
            HotelSearch hotelSearch = objectMapper.readValue(message, HotelSearch.class);
            searchRepository.saveSearch(hotelSearch);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize HotelSearch event", e);
        }
    }
}
