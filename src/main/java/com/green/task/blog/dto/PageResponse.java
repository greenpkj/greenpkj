package com.green.task.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> list;
    private Integer totalPages;
    private long totalElements;
    private int page;
    private int size;
    private boolean last;
    private boolean next;
    private String sort;

    public static PageResponse of(PageImpl<BlogSearchResponse> page, String sort) {
        return new PageResponse(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.isLast(),
                page.hasNext(),
                sort);
    }
}
