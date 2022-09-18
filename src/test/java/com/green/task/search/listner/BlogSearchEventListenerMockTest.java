package com.green.task.search.listner;

import com.green.task.search.dto.SearchEventMessage;
import com.green.task.search.service.SearchEventRedisService;
import com.green.task.search.service.SearchEventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BlogSearchEventListenerMockTest {

    @InjectMocks
    private BlogSearchEventListener listener;

    @Mock
    private SearchEventService eventService;

    @Mock
    private SearchEventRedisService eventRedisService;

    @DisplayName("listenSearchEventMessage test")
    @Test
    public void listenSearchEventMessageTest() {
        //given
        String keyword = "keyword";
        SearchEventMessage message = new SearchEventMessage(keyword, LocalDateTime.now());

        //when
        listener.listenSearchEventMessage(message);

        //then
        verify(eventService, times(1)).saveEvent(message);
        verify(eventRedisService, times(1)).saveEvent(message);
    }
}
