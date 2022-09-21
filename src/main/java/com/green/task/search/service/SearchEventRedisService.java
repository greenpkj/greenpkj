package com.green.task.search.service;

import com.green.task.search.dto.KeywordDto;
import com.green.task.search.dto.SearchEventMessage;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchEventRedisService {
    private final RedissonClient client;
    private static final int RANK_COUNT = 9;
    private static final String REDIS_ZSCORE_KEY = "BLOG:RANK";

    public void saveEvent(SearchEventMessage message) {
        client.getScoredSortedSet(REDIS_ZSCORE_KEY).addScore(message.getKeyword(), 1);
    }

    public List<KeywordDto> findTopTenKeywords() {
        Collection<ScoredEntry<Object>> rank = client.getScoredSortedSet(REDIS_ZSCORE_KEY).entryRangeReversed(0, RANK_COUNT);
        return rank.stream().map(i -> new KeywordDto(i.getValue().toString(), i.getScore())).collect(Collectors.toList());
    }
}
