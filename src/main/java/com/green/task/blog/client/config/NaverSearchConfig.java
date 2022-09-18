package com.green.task.blog.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverSearchConfig {

    private static final String NAVER_CLIENT_ID = "X-Naver-Client-Id";
    private static final String NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";

    @Value(value = "${blog-api.naver.client-id}")
    private String clientId;
    @Value(value = "${blog-api.naver.client-secret}")
    private String clientSecret;

    @Bean
    RequestInterceptor naverRequestInterceptor() {
        return template -> {
            template.header(NAVER_CLIENT_ID, clientId);
            template.header(NAVER_CLIENT_SECRET, clientSecret);
        };
    }
}
