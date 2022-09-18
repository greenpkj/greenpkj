package com.green.task.search.service;

import com.green.task.search.dto.SearchEventMessage;
import com.green.task.search.dto.KeywordDto;
import com.green.task.search.entity.SearchEvent;
import com.green.task.search.repository.SearchEventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SearchEventServiceMockTest {

    @InjectMocks
    private SearchEventService service;

    @Mock
    private SearchEventRepository repository;

    @DisplayName("saveEvent 테스트")
    @Test
    public void saveEventTest() {
        //given
        String keyword = "keyword";
        SearchEventMessage eventMessage = new SearchEventMessage(keyword, LocalDateTime.now());
        given(repository.save(any())).willReturn(new SearchEvent());

        //when
        //then
        service.saveEvent(eventMessage);

    }

    @DisplayName("findTopTenKeywordsTest 테스트")
    @Test
    public void findTopTenKeywordsTest() {
        //given
        List<KeywordDto> list = List.of(new KeywordDto("test", 1L));
        given(repository.findTopKeywords(anyInt())).willReturn(list);

        //when
        List<KeywordDto> result = service.findTopTenSearchEvent();

        //then
        assertNotNull(result);

    }
}
