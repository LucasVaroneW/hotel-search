package com.hotelsearch.infrastructure.adapter.in.web;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SearchRequest(
    @NotBlank String hotelId,
    @NotNull LocalDate checkInDate,
    @NotNull LocalDate checkOutDate,
    @NotEmpty List<Integer> ages
) {
}
