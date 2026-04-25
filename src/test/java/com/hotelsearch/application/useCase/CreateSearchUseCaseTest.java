package com.hotelsearch.application.useCase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hotelsearch.application.usecase.CreateSearchUseCase;
import com.hotelsearch.domain.model.HotelSearch;
import com.hotelsearch.domain.port.out.SearchEventPublisher;

@ExtendWith(MockitoExtension.class)
public class CreateSearchUseCaseTest {
    
    @Mock
    private SearchEventPublisher eventPublisher;

    @InjectMocks
    private CreateSearchUseCase createSearchUseCase;

    private static final LocalDate CHECK_IN_DATE = LocalDate.of(2026, 4, 22);
    private static final LocalDate CHECK_OUT_DATE = LocalDate.of(2026, 4, 25);
    private static final List<Integer> AGES = List.of(30, 28);

    @Test
    void shouldReturnSearchId (){
        String hotelId = "ABC123";
        String searchId = createSearchUseCase.createSearch(hotelId, CHECK_IN_DATE, CHECK_OUT_DATE, AGES);
        assertNotNull(searchId);
        assertDoesNotThrow(() -> UUID.fromString(searchId));
    }

    @Test
    void shouldPublishSearchEvent() {
        String hotelId = "ABC123";
        createSearchUseCase.createSearch(hotelId, CHECK_IN_DATE, CHECK_OUT_DATE, AGES);
        verify(eventPublisher, times(1)).publishSearch(any(HotelSearch.class));
    }


}
