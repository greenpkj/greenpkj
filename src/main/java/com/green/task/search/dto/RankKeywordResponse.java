package com.green.task.search.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class RankKeywordResponse {

    private List<KeywordDto> list;

    public static RankKeywordResponse of(List<KeywordDto> list) {
        return new RankKeywordResponse(list);
    }
}
