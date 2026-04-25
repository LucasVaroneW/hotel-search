package com.hotelsearch.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hotelsearch.domain.port.in.CreateSearchPort;
import com.hotelsearch.domain.port.in.GetSearchCountPort;
import com.hotelsearch.domain.port.in.GetSearchCountPort.SearchCountResult;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final CreateSearchPort createSearchPort;
    private final GetSearchCountPort getSearchCountPort;

    public SearchController(CreateSearchPort createSearchPort, GetSearchCountPort getSearchCountPort) {
        this.createSearchPort = createSearchPort;
        this.getSearchCountPort = getSearchCountPort;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createSearch(@Valid @RequestBody SearchRequest request) {
        String searchId = createSearchPort.createSearch(
            request.hotelId(),
            request.checkInDate(),
            request.checkOutDate(),
            request.ages()
        );
        return Map.of("searchId", searchId);
    }

    @GetMapping("/count")
    public SearchCountResult getCount(@RequestParam String searchId) {
        return getSearchCountPort.getCount(searchId);
    }
}
