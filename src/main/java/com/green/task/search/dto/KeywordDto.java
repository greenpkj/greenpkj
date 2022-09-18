package com.green.task.search.dto;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KeywordDto {

    private String keyword;
    private Long count;

    public KeywordDto(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }


    public KeywordDto(String value, Double score) {
        this.keyword = value;
        this.count = score.longValue();
    }
}
