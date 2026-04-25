package com.hotelsearch.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.hotelsearch.domain.model.HotelSearch;
import com.hotelsearch.domain.port.in.CreateSearchPort;
import com.hotelsearch.domain.port.out.SearchEventPublisher;

public class CreateSearchUseCase implements CreateSearchPort {

    private final SearchEventPublisher eventPublisher;

    public CreateSearchUseCase(SearchEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public String createSearch(
        String hotelId, 
        LocalDate checkInDate,
        LocalDate checkOutDate,
        List<Integer> ages
    ){
        HotelSearch hotelSearch = new HotelSearch(
            UUID.randomUUID().toString(),
            hotelId,
            checkInDate,
            checkOutDate,
            ages
        );
        eventPublisher.publishSearch(hotelSearch);
        return hotelSearch.searchId();
    }
}