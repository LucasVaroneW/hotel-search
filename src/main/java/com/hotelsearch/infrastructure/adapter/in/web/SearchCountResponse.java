package com.hotelsearch.infrastructure.adapter.in.web;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hotelsearch.domain.port.in.GetSearchCountPort.SearchCountResult;

public record SearchCountResponse(
    String searchId,
    SearchDetail search,
    long count
) {
    public record SearchDetail(
        String hotelId,
        @JsonProperty("checkIn") LocalDate checkInDate,
        @JsonProperty("checkOut") LocalDate checkOutDate,
        List<Integer> ages
    ) {}

    public static SearchCountResponse from(SearchCountResult result) {
        SearchDetail detail = new SearchDetail(
            result.hotelSearch().hotelId(),
            result.hotelSearch().checkInDate(),
            result.hotelSearch().checkOutDate(),
            result.hotelSearch().ages()
        );
        return new SearchCountResponse(result.searchId(), detail, result.count());
    }
}
