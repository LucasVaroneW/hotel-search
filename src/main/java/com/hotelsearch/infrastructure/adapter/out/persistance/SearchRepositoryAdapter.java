package com.hotelsearch.infrastructure.adapter.out.persistance;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hotelsearch.domain.model.HotelSearch;
import com.hotelsearch.domain.port.out.SearchRepositoryPort;

@Component
public class SearchRepositoryAdapter implements SearchRepositoryPort {

    private final JpaSearchRepository jpaSearchRepository;

    public SearchRepositoryAdapter(JpaSearchRepository jpaSearchRepository) {
        this.jpaSearchRepository = jpaSearchRepository;
    }

    @Override
    public void saveSearch(HotelSearch hotelSearch) {
        jpaSearchRepository.save(new SearchEntity(
            hotelSearch.searchId(),
            hotelSearch.hotelId(),
            hotelSearch.checkInDate(),
            hotelSearch.checkOutDate(),
            serializeAges(hotelSearch.ages())
        ));
    }

    @Override
    public Optional<HotelSearch> findSearchById(String searchId) {
        return jpaSearchRepository.findById(searchId).map(this::toDomain);
    }

    @Override
    public long countSearchesByHotelId(HotelSearch hotelSearch) {
        return jpaSearchRepository.countByHotelIdAndCheckInDateAndCheckOutDateAndAges(
            hotelSearch.hotelId(),
            hotelSearch.checkInDate(),
            hotelSearch.checkOutDate(),
            serializeAges(hotelSearch.ages())
        );
    }

    private String serializeAges(List<Integer> ages) {
        return ages.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    private HotelSearch toDomain(SearchEntity entity) {
        List<Integer> ages = Arrays.stream(entity.getAges().split(","))
            .map(Integer::parseInt)
            .toList();
        return new HotelSearch(
            entity.getSearchId(),
            entity.getHotelId(),
            entity.getCheckInDate(),
            entity.getCheckOutDate(),
            ages
        );
    }
}