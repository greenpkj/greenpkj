package com.green.task.blog.service;

import com.green.task.blog.client.KakaoBlogSearchClient;
import com.green.task.blog.client.NaverBlogSearchClient;
import com.green.task.blog.dto.*;
import com.green.task.search.dto.SearchEventMessage;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogSearchService implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;
    private final KakaoBlogSearchClient kakaoClient;
    private final NaverBlogSearchClient naverClient;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @CircuitBreaker(name = "search", fallbackMethod = "fallback")
    public PageResponse search(BlogSearchDto.KakaoDto dto) {
        log.info("start to search : {} ", dto);
        final KakaoSearchResponse searchRes = kakaoClient.search(dto);
        return convertResponse(searchRes, dto);
    }

    private PageResponse convertResponse(KakaoSearchResponse saerchResponse, BlogSearchDto.KakaoDto dto) {
        List<BlogSearchResponse> list = saerchResponse.getDocuments().stream().map(BlogSearchResponse::toResponse).collect(Collectors.toList());
        eventPublisher.publishEvent(new SearchEventMessage(dto.getQuery(), LocalDateTime.now()));
        PageImpl<BlogSearchResponse> page = new PageImpl<>(list, PageRequest.of(dto.getPage(), dto.getSize(), Sort.by(dto.getSort()).descending()), saerchResponse.getMeta().getTotalCount());

        return PageResponse.of(page, dto.getSort());
    }

    private PageResponse fallback(BlogSearchDto.KakaoDto dto, Exception e) {

        log.error("error occurred : {}", e);
        NaverSearchResponse naverResponse = naverClient.search(BlogSearchDto.NaverDto.toNaverDto(dto));
        KakaoSearchResponse kakaoResponse = naverResponse.toKakaoResponse();
        return convertResponse(kakaoResponse, dto);
    }

}
