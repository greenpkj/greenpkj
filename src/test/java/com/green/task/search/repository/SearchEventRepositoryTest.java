package com.green.task.search.repository;

import com.green.task.search.entity.SearchEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@Transactional DataJpaTest 내부적으로 Transactional을 가지고 있기때문에 필요하지 않음
@DataJpaTest
public class SearchEventRepositoryTest {

    @Autowired
    private SearchEventRepository repository;

    @DisplayName("SearchEventRepository save test ")
    @Test
    public void saveTest() {

        //given
        String keyword = "test";

        //when
        SearchEvent test = repository.save(SearchEvent.toEntity(keyword));

        //then
        assertTrue(repository.findById(test.getId()).isPresent());
    }

    @DisplayName("SearchEventRepository rank test ")
    @Test
    public void rankTest() {

        //given
        String keywordFirst = "first";
        String keywordSecond = "second";

        repository.save(SearchEvent.toEntity(keywordFirst));
        repository.save(SearchEvent.toEntity(keywordSecond));

        //when
        List<SearchEvent> result = repository.findAll();
        //then
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }
}
