package com.hotelsearch.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hotelsearch.domain.exception.BusinessRuleException;

class HotelSearchTest {
    
    private static final LocalDate CHECK_IN_DATE = LocalDate.of(2026, 4, 22);
    private static final LocalDate CHECK_OUT_DATE = LocalDate.of(2026, 4, 25);
    private static final List<Integer> AGES = List.of(30, 28);


    @Test
    void shouldCreateHotelSearchSuccessFully() {
        HotelSearch hotelSearch = new HotelSearch("searchId-3", "ABC123", CHECK_IN_DATE, CHECK_OUT_DATE, AGES);
        assertAll(
            () -> assertEquals("searchId-3", hotelSearch.searchId()),
            () -> assertEquals("ABC123", hotelSearch.hotelId()),
            () -> assertEquals(CHECK_IN_DATE, hotelSearch.checkInDate()),
            () -> assertEquals(CHECK_OUT_DATE, hotelSearch.checkOutDate()),
            () -> assertEquals(AGES, hotelSearch.ages())
        );
    }


    @Test
    void shouldThrowExceptionWhenCheckInDateIsAfterCheckOutDate() {
        assertThrows(BusinessRuleException.class, () -> {
            new HotelSearch("searchId-3", "ABC123", CHECK_OUT_DATE, CHECK_IN_DATE, AGES);
        });
    }

    @Test
    void shouldBeInmutable(){
        List<Integer> ages = new ArrayList<>(List.of(30, 28));
        HotelSearch hotelSearch = new HotelSearch("searchId-3", "ABC123", CHECK_IN_DATE, CHECK_OUT_DATE, ages);
        ages.add(7);
        assertEquals(List.of(30, 28), hotelSearch.ages());
    }
}
