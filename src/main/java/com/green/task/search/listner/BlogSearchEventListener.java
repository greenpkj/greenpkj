package com.green.task.search.listner;

import com.green.task.search.dto.SearchEventMessage;
import com.green.task.search.service.SearchEventRedisService;
import com.green.task.search.service.SearchEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogSearchEventListener {

    private final SearchEventService eventService;
    private final SearchEventRedisService eventRedisService;

    @Async(value = "searchPoolTaskExecutor")
    @EventListener(classes = SearchEventMessage.class)
    public void listenSearchEventMessage(SearchEventMessage message) {

        log.info(message.toString());
        //save to DB
        eventService.saveEvent(message);
        //save to Redis
        eventRedisService.saveEvent(message);
    }

}
