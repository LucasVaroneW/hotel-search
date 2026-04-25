package com.hotelsearch.domain.port.in;

import java.time.LocalDate;
import java.util.List;

public interface CreateSearchPort {
    String createSearch(
        String hotelId, 
        LocalDate checkInDate,
        LocalDate checkOutDate,
        List<Integer> ages
    );
}
