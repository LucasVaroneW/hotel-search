package com.hotelsearch.infrastructure.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hotelsearch.domain.model.HotelSearch;
import com.hotelsearch.domain.port.in.CreateSearchPort;
import com.hotelsearch.domain.port.in.GetSearchCountPort;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateSearchPort createSearchPort;

    @MockitoBean
    private GetSearchCountPort getSearchCountPort;

    private static final LocalDate CHECK_IN_DATE = LocalDate.of(2026, 4, 22);
    private static final LocalDate CHECK_OUT_DATE = LocalDate.of(2026, 4, 25);
    private static final List<Integer> AGES = List.of(30, 28);


    @Test
    void shouldCreateSearchAndReturnSearchId() throws Exception {

        when(createSearchPort.createSearch(any(), any(), any(), any()))
            .thenReturn("id-1");

        mockMvc.perform(post("/search")
            .contentType("application/json")
            .content("""
                {
                    "hotelId": "ABC123",
                    "checkInDate": "2026-04-22",
                    "checkOutDate": "2026-04-25",
                    "ages": [30, 28]
                }
            """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.searchId").value("id-1")
        );
    }

    @Test
    void shouldReturnSearchCountsOfHotelSearch() throws Exception {

        HotelSearch hotelSearch = new HotelSearch(
            "id-1", "ABC123", CHECK_IN_DATE, CHECK_OUT_DATE, AGES
        );

        GetSearchCountPort.SearchCountResult result = new GetSearchCountPort.SearchCountResult("id-1", hotelSearch, 5L);

        when(getSearchCountPort.getCount("id-1"))
            .thenReturn(result);
        
        mockMvc.perform(get("/search/count")
            .param("searchId", "id-1")
            .contentType("application/json")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.searchId").value("id-1"))
            .andExpect(jsonPath("$.count").value(5L)
        );
    }
    
}
