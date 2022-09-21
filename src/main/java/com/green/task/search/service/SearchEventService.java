package com.green.task.search.service;

import com.green.task.search.dto.KeywordDto;
import com.green.task.search.dto.SearchEventMessage;
import com.green.task.search.entity.SearchEvent;
import com.green.task.search.repository.SearchEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchEventService {
    private final SearchEventRepository repository;
    private static final int RANK_COUNT = 10;

    @Transactional
    public void saveEvent(SearchEventMessage message) {
        repository.save(SearchEvent.toEntity(message.getKeyword()));
    }

    public List<KeywordDto> findTopTenSearchEvent() {
        return repository.findTopKeywords(RANK_COUNT);
    }

}
