package com.green.task.blog.client;


import com.green.task.blog.client.config.KakaoSearchConfig;
import com.green.task.blog.dto.BlogSearchDto;
import com.green.task.blog.dto.KakaoSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "kakao-blog-api",
        url = "${blog-api.kakao.host}",
        configuration = KakaoSearchConfig.class
)
public interface KakaoBlogSearchClient extends BlogClient<BlogSearchDto.KakaoDto, KakaoSearchResponse> {

    @Override
    @GetMapping("/v2/search/blog")
    KakaoSearchResponse search(@SpringQueryMap BlogSearchDto.KakaoDto dto);
}
