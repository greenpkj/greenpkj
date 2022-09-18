package com.green.task.blog.client;

import com.green.task.blog.dto.BlogSearchDto;
import com.green.task.blog.dto.KakaoSearchResponse;
import com.green.task.blog.dto.NaverSearchResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BlogSearchClientTest {
    @Autowired
    private KakaoBlogSearchClient kakaoBlogSearchClient;

    @Autowired
    private NaverBlogSearchClient naverBlogSearchClient;

    @DisplayName("카카오 블로그 검색 test")
    @Test
    public void kakaoSearchTest() {

        //given
        String keyword = "카카오";
        BlogSearchDto.KakaoDto dto = new BlogSearchDto.KakaoDto(
                keyword,
                1,
                10,
                "accuracy"
        );

        //when
        KakaoSearchResponse response = kakaoBlogSearchClient.search(dto);

        //then
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getDocuments().isEmpty());
        Assertions.assertNotNull(response.getMeta());
    }

    @DisplayName("네이버 블로그 검색 test")
    @Test
    public void naverSearchTest() {

        //given
        String keyword = "네이버";
        BlogSearchDto.NaverDto dto = new BlogSearchDto.NaverDto(
                keyword,
                10,
                1,
                "sim"
        );

        //when
        NaverSearchResponse response = naverBlogSearchClient.search(dto);
        //then
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getItems().isEmpty());
        Assertions.assertNotNull(response.getLastBuildDate());
    }
}
