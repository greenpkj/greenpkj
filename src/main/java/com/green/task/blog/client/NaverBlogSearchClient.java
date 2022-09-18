package com.green.task.blog.client;


import com.green.task.blog.client.config.NaverSearchConfig;
import com.green.task.blog.dto.BlogSearchDto;
import com.green.task.blog.dto.NaverSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "naver-blog-api",
        url = "${blog-api.naver.host}",
        configuration = NaverSearchConfig.class

)
public interface NaverBlogSearchClient extends BlogClient<BlogSearchDto.NaverDto, NaverSearchResponse> {

    @Override
    @GetMapping("/v1/search/blog.json")
    NaverSearchResponse search(@SpringQueryMap BlogSearchDto.NaverDto blogSearchDto);
}
