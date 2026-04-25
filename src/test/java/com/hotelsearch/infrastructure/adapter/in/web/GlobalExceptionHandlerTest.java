package com.hotelsearch.infrastructure.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hotelsearch.domain.exception.BusinessRuleException;
import com.hotelsearch.domain.port.in.CreateSearchPort;
import com.hotelsearch.domain.port.in.GetSearchCountPort;

@WebMvcTest(SearchController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateSearchPort createSearchPort;

    @MockitoBean
    private GetSearchCountPort getSearchCountPort;

    @Test
    void shouldReturn400WhenBusinessRuleExceptionIsThrown() throws Exception {
        when(createSearchPort.createSearch(any(), any(), any(), any()))
            .thenThrow(new BusinessRuleException("Check-in date must be before check-out date."));

        mockMvc.perform(post("/search")
                .contentType("application/json")
                .content("""
                    {
                        "hotelId": "ABC123",
                        "checkInDate": "2026-04-25",
                        "checkOutDate": "2026-04-22",
                        "ages": [30, 28]
                    }
                """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Check-in date must be before check-out date."));
    }
}

