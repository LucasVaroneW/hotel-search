package com.hotelsearch.infrastructure.adapter.out.messaging;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelsearch.domain.model.HotelSearch;

@ExtendWith(MockitoExtension.class)
class KafkaSearchEventPublisherTest {

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;
    
    @Mock
    ObjectMapper objectMapper;
    
    @InjectMocks
    KafkaSearchEventPublisher publisher;

    private static final HotelSearch SEARCH = new HotelSearch(
        "id-1", "ABC123",
        LocalDate.of(2026, 4, 22), LocalDate.of(2026, 4, 25),
        List.of(30, 28)
    );

    @Test
    void shouldPublishSerializedMessage() throws Exception {
        when(objectMapper.writeValueAsString(SEARCH)).thenReturn("{\"searchId\":\"id-1\"}");

        publisher.publishSearch(SEARCH);

        verify(kafkaTemplate).send("hotel_availability_searches", "id-1", "{\"searchId\":\"id-1\"}");
    }
}

