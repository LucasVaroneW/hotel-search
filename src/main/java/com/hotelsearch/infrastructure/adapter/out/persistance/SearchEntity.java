package com.hotelsearch.infrastructure.adapter.out.persistance;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotel_searches")
public class SearchEntity {

    @Id
    private String searchId;
    private String hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String ages;

    protected SearchEntity() {}

    public SearchEntity(String searchId, String hotelId, LocalDate checkInDate, LocalDate checkOutDate, String ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.ages = ages;
    }

    public String getSearchId() { return searchId; }
    public String getHotelId() { return hotelId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public String getAges() { return ages; }
}
