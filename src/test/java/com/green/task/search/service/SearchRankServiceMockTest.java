package com.green.task.search.service;

import com.green.task.search.dto.KeywordDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SearchRankServiceMockTest {


    @InjectMocks
    private SearchRankService service;
    @Mock
    private SearchEventService searchEventService;

    @Mock
    private SearchEventRedisService eventRedisService;

    @DisplayName("getTopRanks 테스트")
    @Test
    public void getTopRanksTest() {
        //given
        List<KeywordDto> list = List.of(new KeywordDto("test", 1L));
        given(searchEventService.findTopTenSearchEvent()).willReturn(list);

        //when
        List<KeywordDto> result = service.getTopRanks();

        //then
        assertNotNull(result);

    }

    @DisplayName("getTopRanksByRedis 테스트")
    @Test
    public void getTopRanksByRedisTest() {
        //given
        List<KeywordDto> list = List.of(new KeywordDto("test", 1L));
        given(eventRedisService.findTopTenKeywords()).willReturn(list);

        //when
        List<KeywordDto> result = service.getTopRanksByRedis();

        //then
        assertNotNull(result);

    }


}
