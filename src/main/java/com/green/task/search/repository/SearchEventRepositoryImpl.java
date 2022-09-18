package com.green.task.search.repository;


import com.green.task.search.dto.KeywordDto;
import com.green.task.search.entity.QSearchEvent;
import com.green.task.search.entity.SearchEvent;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SearchEventRepositoryImpl extends QuerydslRepositorySupport implements SearchEventRepositoryWrapper {

    public SearchEventRepositoryImpl() {
        super(SearchEvent.class);
    }

    private static final QSearchEvent SEARCH_EVENT_TABLE = QSearchEvent.searchEvent;

    @Override
    public List<KeywordDto> findTopKeywords(int limit) {
        return from(SEARCH_EVENT_TABLE)
                .select(
                        Projections.constructor(KeywordDto.class,
                                SEARCH_EVENT_TABLE.keyword,
                                SEARCH_EVENT_TABLE.count()
                        )
                )
                .groupBy(SEARCH_EVENT_TABLE.keyword)
                .orderBy(SEARCH_EVENT_TABLE.keyword.count().desc())
                .limit(limit)
                .fetch();
    }

}
