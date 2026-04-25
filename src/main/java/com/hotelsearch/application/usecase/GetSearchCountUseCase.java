package com.hotelsearch.application.usecase;

import com.hotelsearch.domain.exception.BusinessRuleException;
import com.hotelsearch.domain.port.in.GetSearchCountPort;
import com.hotelsearch.domain.port.in.GetSearchCountPort.SearchCountResult;
import com.hotelsearch.domain.port.out.SearchRepositoryPort;

public class GetSearchCountUseCase implements GetSearchCountPort {

    private final SearchRepositoryPort searchRepositoryPort;

    public GetSearchCountUseCase(SearchRepositoryPort searchRepositoryPort) {
        this.searchRepositoryPort = searchRepositoryPort;
    }
    
    @Override
    public SearchCountResult getCount(String searchId) {
        return searchRepositoryPort.findSearchById(searchId)
            .map(search -> new SearchCountResult(
                searchId,
                search,
                searchRepositoryPort.countSearchesByHotelId(search)
            ))
            .orElseThrow(() -> new BusinessRuleException("Search not found: " + searchId));
    }
}
