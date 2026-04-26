package com.hotelsearch.infrastructure.adapter.out.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.hotelsearch.domain.model.HotelSearch;

@DataJpaTest
@ActiveProfiles("test")
public class SearchRepositoryAdapterTest {
    
    @Autowired
    private JpaSearchRepository jpaSearchRepository;

    private SearchRepositoryAdapter searchRepositoryAdapter;

    private static final HotelSearch SEARCH = new HotelSearch(
        "id-1", "ABC123",
        LocalDate.of(2026, 4, 22), LocalDate.of(2026, 4, 25),
        List.of(30, 28)
    );

    @BeforeEach
    void setUp() {
        searchRepositoryAdapter = new SearchRepositoryAdapter(jpaSearchRepository);
    }

    @Test
    void shouldSaveAndFindSearch() {
        searchRepositoryAdapter.saveSearch(SEARCH);

        Optional<HotelSearch> result = searchRepositoryAdapter.findSearchById(SEARCH.searchId());

        assertTrue(result.isPresent());
        assertEquals(SEARCH, result.get());
    }

    @Test
    void shouldReturnEmptyWhenSearchNotFound() {
        Optional<HotelSearch> result = searchRepositoryAdapter.findSearchById("no-existe");
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldCountIdenticalSearches() {
        searchRepositoryAdapter.saveSearch(SEARCH);
        searchRepositoryAdapter.saveSearch(new HotelSearch("id-2", "ABC123",
        LocalDate.of(2026, 4, 22), LocalDate.of(2026, 4, 25),
        List.of(30, 28)));
        searchRepositoryAdapter.saveSearch(new HotelSearch("id-3", "ABC123",
        LocalDate.of(2026, 4, 22), LocalDate.of(2026, 4, 25),
        List.of(28, 30)));

        long count = searchRepositoryAdapter.countSearchesByHotelId(SEARCH);

        assertEquals(2, count);
    }

    @Test
    void shouldNotCountDifferentAgesOrder() {
        searchRepositoryAdapter.saveSearch(SEARCH);
        HotelSearch different = new HotelSearch("id-2", "ABC123",
            LocalDate.of(2026, 4, 22), LocalDate.of(2026, 4, 25),
            List.of(28, 30));
        searchRepositoryAdapter.saveSearch(different);

        assertEquals(1L, searchRepositoryAdapter.countSearchesByHotelId(SEARCH));
    }

}
