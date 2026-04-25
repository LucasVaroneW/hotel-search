package com.hotelsearch.domain.port.in;

import com.hotelsearch.domain.model.HotelSearch;

public interface GetSearchCountPort {
    SearchCountResult getCount(String searchId);

    record SearchCountResult(String searchId, HotelSearch hotelSearch, long count) {}
}
