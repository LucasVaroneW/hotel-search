package com.hotelsearch.infrastructure.adapter.out.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelsearch.domain.model.HotelSearch;
import com.hotelsearch.domain.port.out.SearchEventPublisher;

@Component
public class KafkaSearchEventPublisher implements SearchEventPublisher {
    
    private final KafkaTemplate<String, String> kafkaTemplate; 
    private final ObjectMapper objectMapper;

    public KafkaSearchEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishSearch(HotelSearch hotelSearch) {
        try {
            String hotelSearchString = objectMapper.writeValueAsString(hotelSearch);
            kafkaTemplate.send("hotel_availability_searches", hotelSearch.searchId(), hotelSearchString);         
            
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize HotelSearch event", e);
        }
    }
}
