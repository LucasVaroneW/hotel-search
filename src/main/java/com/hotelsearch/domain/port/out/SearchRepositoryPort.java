package com.hotelsearch.domain.port.out;

import java.util.Optional;

import com.hotelsearch.domain.model.HotelSearch;

public interface SearchRepositoryPort {
    void saveSearch(HotelSearch hotelSearch);
    Optional<HotelSearch> findSearchById(String searchId);
    long countSearchesByHotelId(HotelSearch hotelSearch);
}
