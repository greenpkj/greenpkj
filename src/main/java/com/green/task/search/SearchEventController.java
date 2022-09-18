package com.green.task.search;

import com.green.task.search.dto.RankKeywordResponse;
import com.green.task.search.service.SearchRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchEventController {

    private final SearchRankService rankService;

    @GetMapping("/v1/search/rank")
    public RankKeywordResponse getTopTenRank() {
        return RankKeywordResponse.of(rankService.getTopRanks());
    }

    @GetMapping("/v2/search/rank")
    public RankKeywordResponse getTopTenRankByRedis() {
        return RankKeywordResponse.of(rankService.getTopRanksByRedis());
    }
}
