package com.green.task.blog.service;


import com.green.task.blog.client.KakaoBlogSearchClient;
import com.green.task.blog.dto.BlogSearchDto;
import com.green.task.blog.dto.PageResponse;
import com.green.task.search.dto.SearchEventMessage;
import com.green.task.search.listner.BlogSearchEventListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RecordApplicationEvents
public class BlogSearchServiceTest {

    @Autowired
    private BlogSearchService blogSearchService;
    @Autowired
    private ApplicationEvents events;
    @Autowired
    private BlogSearchEventListener eventListener;

    @MockBean
    private KakaoBlogSearchClient kakaoBlogSearchClient;

    @DisplayName("event listener 정상 발행 테스트")
    @Test
    public void blogSearchService_eventListenerTest() {
        //given
        String keyword = "카카오";
        BlogSearchDto.KakaoDto dto = new BlogSearchDto.KakaoDto(
                keyword,
                1,
                10,
                "accuracy"
        );
        //when
        PageResponse result = blogSearchService.search(dto);

        //then
        long count = events.stream(SearchEventMessage.class).count();
        assertEquals(1, count);
        assertNotNull(result);
    }
}
