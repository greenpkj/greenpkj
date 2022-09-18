package com.green.task.blog.service;


import com.green.task.blog.client.KakaoBlogSearchClient;
import com.green.task.blog.client.NaverBlogSearchClient;
import com.green.task.blog.dto.BlogSearchDto;
import com.green.task.blog.dto.NaverSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BlogSearchFallBackServiceTest {
    @Autowired
    private BlogSearchService service;

    @MockBean
    private ApplicationEventPublisher publisher;
    @MockBean
    private KakaoBlogSearchClient kakaoClient;
    @MockBean
    private NaverBlogSearchClient naverClient;


    @DisplayName("카카오 블로그 검색 실패 fallback test -> naver 검색으로 대체")
    @Test
    public void blogSearchFallBackTest() {
        //given
        String keyword = "카카오";
        BlogSearchDto.KakaoDto dto = new BlogSearchDto.KakaoDto(keyword, 1, 10, "accuracy");

        given(kakaoClient.search(dto)).willThrow(new RuntimeException("fail to get data"));
        given(naverClient.search(any())).willReturn(generateNaverResponse());
        doNothing().when(publisher).publishEvent(any());

        //when
        service.search(dto);

        //then
        verify(kakaoClient, times(1)).search(any());
        verify(naverClient, times(1)).search(any());
    }

    private NaverSearchResponse generateNaverResponse() {

        NaverSearchResponse.NaverItem item = new NaverSearchResponse.NaverItem("title", "link", "description", "bloggerName", "bloggerLink", "postDate");
        List<NaverSearchResponse.NaverItem> items = List.of(item);
        NaverSearchResponse response = new NaverSearchResponse("lastbuildDate", 1, 1, 1, items);

        return response;

    }

}
