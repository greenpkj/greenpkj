package com.green.task.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogSearchResponse {
    private String blogger;
    private String contents;
    private String time;
    private String thumbnail;
    private String title;
    private String url;

    public static BlogSearchResponse toResponse(KakaoSearchResponse.KaKaoDocument document) {
        return new BlogSearchResponse(document.getBlogName(),
                document.getContents(),
                document.getDatetime(),
                document.getThumbnail(),
                document.getTitle(),
                document.getUrl());
    }

}
