package com.hotelsearch.application.useCase;

import com.hotelsearch.application.usecase.GetSearchCountUseCase;
import com.hotelsearch.domain.exception.BusinessRuleException;
import com.hotelsearch.domain.model.HotelSearch;
import com.hotelsearch.domain.port.in.GetSearchCountPort.SearchCountResult;
import com.hotelsearch.domain.port.out.SearchRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetSearchCountUseCaseTest {

    @Mock
    private SearchRepositoryPort searchRepositoryPort;

    @InjectMocks
    private GetSearchCountUseCase getSearchCountUseCase;

    private static final HotelSearch SEARCH = new HotelSearch(
        "id-1", "ABC123",
        LocalDate.of(2023, 12, 29),
        LocalDate.of(2023, 12, 31),
        List.of(30, 29, 1, 3)
    );

    @Test
    void shouldReturnCountResult() {
        when(searchRepositoryPort.findSearchById("id-1")).thenReturn(Optional.of(SEARCH));
        when(searchRepositoryPort.countSearchesByHotelId(SEARCH)).thenReturn(5L);

        SearchCountResult result = getSearchCountUseCase.getCount("id-1");

        assertAll(
            () -> assertEquals("id-1", result.searchId()),
            () -> assertEquals(SEARCH, result.hotelSearch()),
            () -> assertEquals(5L, result.count())
        );
    }

    @Test
    void shouldThrowWhenSearchNotFound() {
        when(searchRepositoryPort.findSearchById("no-existe")).thenReturn(Optional.empty());
        assertThrows(BusinessRuleException.class, () -> getSearchCountUseCase.getCount("no-existe"));
    }
}
