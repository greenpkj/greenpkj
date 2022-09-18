package com.green.task.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BlogSearchDto {

    @Getter
    @AllArgsConstructor
    @ToString
    public static class KakaoDto {
        private String query;
        private Integer page;
        private Integer size;
        private String sort;

        public static KakaoDto toKakaoDto(String query, Integer page, Integer size, String sort) {

            return new KakaoDto(query, page, size, sort);
        }
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class NaverDto {

        private String query;
        private Integer display;
        private Integer start;
        private String sort;

        public static NaverDto toNaverDto(KakaoDto kakaoDto) {
            String sort;
            if ("accuracy".equals(kakaoDto.sort)) {
                sort = "sim";
            } else {
                sort = "date";
            }
            return new NaverDto(kakaoDto.getQuery(), kakaoDto.getSize(), kakaoDto.getPage(), sort);
        }

    }
}
