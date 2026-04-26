package com.hotelsearch.infrastructure.adapter.out.persistance;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSearchRepository extends JpaRepository<SearchEntity, String> {

    long countByHotelIdAndCheckInDateAndCheckOutDateAndAges(
        String hotelId, LocalDate checkInDate, LocalDate checkOutDate, String ages
    );
}
