package com.hotelsearch.domain.model;

import com.hotelsearch.domain.exception.BusinessRuleException;

import java.time.LocalDate;
import java.util.List;

public record HotelSearch(
    String searchId,
    String hotelId,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    List<Integer> ages
) {
    public HotelSearch {
        if (checkInDate.isAfter(checkOutDate)) {
            throw new BusinessRuleException("Check-in date must be before check-out date.");
        }
        ages = List.copyOf(ages);
    }
}
