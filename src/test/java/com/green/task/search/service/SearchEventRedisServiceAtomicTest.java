package com.green.task.search.service;

import com.green.task.search.dto.SearchEventMessage;
import com.green.task.search.dto.KeywordDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SearchEventRedisServiceAtomicTest {

    @Autowired
    private SearchEventRedisService service;

    @Test
    public void atomicTest() throws InterruptedException {

        //given
        String keyword = "testgreendpkj";
        SearchEventMessage message = new SearchEventMessage(keyword, LocalDateTime.now());

        int numberOfThreads = 100;
        ExecutorService executeService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        //when
        for (int i = 0; i < numberOfThreads; i++) {
            executeService.submit(() -> {
                service.saveEvent(message);
                latch.countDown();
            });
        }
        latch.await();

        //then
        List<KeywordDto> topTenKeywords = service.findTopTenKeywords();
        assertNotNull(topTenKeywords);
        assertEquals(100, topTenKeywords.get(0).getCount());
    }
}
