package com.green.task.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;

@Getter
@AllArgsConstructor
public class PageResponse {

    private Integer totalPages;
    private long totalElements;
    private int page;
    private int size;
    private boolean last;
    private boolean next;
    private String sort;

    public static PageResponse of(PageImpl<?> page, String sort) {
        return new PageResponse(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.isLast(),
                page.hasNext(),
                sort);
    }
}
