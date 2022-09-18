package com.green.task.search.service;

import com.green.task.search.dto.KeywordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchRankService {

    private final SearchEventService searchEventService;
    private final SearchEventRedisService eventRedisService;

    public List<KeywordDto> getTopRanks() {
        return searchEventService.findTopTenSearchEvent();
    }

    public List<KeywordDto> getTopRanksByRedis() {
        return eventRedisService.findTopTenKeywords();
    }
}
