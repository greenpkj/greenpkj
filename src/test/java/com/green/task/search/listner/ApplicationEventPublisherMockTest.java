package com.green.task.search.listner;

import com.green.task.search.dto.SearchEventMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ApplicationEventPublisherMockTest {

    @Autowired
    private ApplicationEventPublisher publisher;

    @MockBean
    private BlogSearchEventListener eventListener;

    @DisplayName("BlogSearchEventListener 정상 event listen test")
    @Test
    public void eventListenerMockTest() {
        //given
        SearchEventMessage keyword = new SearchEventMessage("keyword", LocalDateTime.now());

        //when
        publisher.publishEvent(keyword);

        //then
        verify(eventListener, times(1)).listenSearchEventMessage(keyword);
    }
}
