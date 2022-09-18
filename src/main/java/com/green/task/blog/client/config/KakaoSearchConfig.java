package com.green.task.blog.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoSearchConfig {

    static final String KAKAO_AUTHORIZATION = "Authorization";
    @Value(value = "${blog-api.kakao.apikey}")
    private String apiKey;

    @Bean
    RequestInterceptor kakaoRequestInterceptor() {
        return template -> template.header(KAKAO_AUTHORIZATION, "KakaoAK " + apiKey);
    }
}
