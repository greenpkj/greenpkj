package com.green.task.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverSearchResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverItem> items;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NaverItem {
        private String title;
        private String link;
        private String description;
        @JsonProperty("bloggername")
        private String bloggerName;
        @JsonProperty("bloggerlink")
        private String bloggerLink;
        @JsonProperty("postdate")
        private String postDate;
    }

    public KakaoSearchResponse toKakaoResponse() {
        List<KakaoSearchResponse.KaKaoDocument> kaKaoDocuments = items.stream().map(this::convertToKakaoDoc).collect(Collectors.toList());

        KakaoSearchResponse.Meta meta = new KakaoSearchResponse.Meta(false, 1, total);
        return new KakaoSearchResponse(kaKaoDocuments, meta);
    }

    private KakaoSearchResponse.KaKaoDocument convertToKakaoDoc(NaverItem item) {
        return new KakaoSearchResponse.KaKaoDocument(
                item.bloggerName,
                item.description,
                item.postDate,
                item.title,
                item.link
        );

    }
}
