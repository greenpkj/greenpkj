package com.green.task.blog;

import com.green.task.blog.dto.BlogSearchDto;
import com.green.task.blog.dto.PageResponse;
import com.green.task.blog.service.BlogSearchService;
import com.green.task.config.error.AppErrorCode;
import com.green.task.config.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RequestMapping("/v1/blogs")
@RestController
@RequiredArgsConstructor
@Validated
public class BlogSearchController {

    private static final String QUERY_VALID_MESSAGE = "query field must has a keyword for search";
    private static final String PAGE_MIN_VALID_MESSAGE = "size should be under 50";
    private static final String SIZE_MAX_VALID_MESSAGE = "size should be under 50";

    private static final List<String> SORT_OPTIONS = List.of("accuracy", "recency");

    private final BlogSearchService blogSearchService;

    @GetMapping
    public PageResponse search(
            @NotBlank(message = QUERY_VALID_MESSAGE) @RequestParam String query,
            @Min(value = 1, message = PAGE_MIN_VALID_MESSAGE) @RequestParam(defaultValue = "1", required = false) int page,
            @Max(value = 50, message = SIZE_MAX_VALID_MESSAGE) @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "accuracy", required = false) String sort

    ) {
        validateSort(sort);
        return blogSearchService.search(BlogSearchDto.KakaoDto.toKakaoDto(query, page, size, sort));
    }

    private void validateSort(String sort) {
        if (!SORT_OPTIONS.contains(sort)) {
            throw new BadRequestException(AppErrorCode.CHECK_QUERY_SORT_OPTION);
        }
    }
}
