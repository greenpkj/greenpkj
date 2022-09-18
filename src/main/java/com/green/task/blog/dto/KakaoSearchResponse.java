package com.green.task.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoSearchResponse {

    private List<KaKaoDocument> documents;
    private Meta meta;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KaKaoDocument {
        @JsonProperty("blogname")
        private String blogName;
        private String contents;
        private String datetime;
        private String thumbnail;
        private String title;
        private String url;

        public KaKaoDocument(String blogName, String contents, String datetime, String title, String url) {
            this.blogName = blogName;
            this.contents = contents;
            this.datetime = datetime;
            this.title = title;
            this.url = url;
        }

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        @JsonProperty("is_end")
        private boolean isEnd;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("total_count")
        private int totalCount;
    }


}
