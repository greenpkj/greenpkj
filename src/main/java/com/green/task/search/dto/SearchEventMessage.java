package com.green.task.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class SearchEventMessage {

    private String keyword;
    private LocalDateTime date;
}
