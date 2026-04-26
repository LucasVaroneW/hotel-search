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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelsearch.domain.model.HotelSearch;
import com.hotelsearch.domain.port.out.SearchRepositoryPort;

@ExtendWith(MockitoExtension.class)
class KafkaSearchConsumerTest {

    @Mock
    SearchRepositoryPort searchRepositoryPort;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    KafkaSearchConsumer consumer;

    private static final HotelSearch SEARCH = new HotelSearch(
        "id-1", "ABC123",
        LocalDate.of(2026, 4, 22), LocalDate.of(2026, 4, 25),
        List.of(30, 28)
    );

    @Test
    void shouldDeserializeAndSave() throws Exception {
        String json = "{\"searchId\":\"id-1\"}";
        when(objectMapper.readValue(json, HotelSearch.class)).thenReturn(SEARCH);

        consumer.consumeSearchEvent(json);

        verify(searchRepositoryPort).saveSearch(SEARCH);
    }
}