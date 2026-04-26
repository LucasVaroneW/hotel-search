package com.hotelsearch.infrastructure.adapter.in.web;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SearchRequest(
    @NotBlank String hotelId,
    @JsonProperty("checkIn") @JsonFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkInDate,
    @JsonProperty("checkOut") @JsonFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkOutDate,
    @NotEmpty List<Integer> ages
) {
}
