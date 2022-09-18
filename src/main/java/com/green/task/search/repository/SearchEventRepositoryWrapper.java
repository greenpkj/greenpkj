package com.green.task.search.repository;


import com.green.task.search.dto.KeywordDto;

import java.util.List;

public interface SearchEventRepositoryWrapper {

    List<KeywordDto> findTopKeywords(int limit);
}
