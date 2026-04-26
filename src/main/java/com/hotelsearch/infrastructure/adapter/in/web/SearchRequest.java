package com.hotelsearch.infrastructure.adapter.in.web;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SearchRequest(
    @NotBlank String hotelId,
    @JsonProperty("checkIn") @NotNull LocalDate checkInDate,
    @JsonProperty("checkOut") @NotNull LocalDate checkOutDate,
    @NotEmpty List<Integer> ages
) {
}
