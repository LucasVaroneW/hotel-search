package com.hotelsearch.domain.port.out;

import com.hotelsearch.domain.model.HotelSearch;

public interface SearchEventPublisher {
    void publishSearch(HotelSearch hotelSearch);
}
