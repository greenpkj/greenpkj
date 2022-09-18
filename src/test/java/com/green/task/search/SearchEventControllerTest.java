package com.green.task.search;

import com.green.task.search.dto.SearchEventMessage;
import com.green.task.search.service.SearchEventRedisService;
import com.green.task.search.service.SearchEventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "localhost")
public class SearchEventControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private SearchEventService eventService;
    @Autowired
    private SearchEventRedisService eventRedisService;

    @DisplayName("/v1/search/rank v1 테스트")
    @Test
    public void searchTopTenKeyword_v1() throws Exception {

        //given
        //데이터가 없으므로 데이터를 밀어 넣어줌
        eventService.saveEvent(new SearchEventMessage("날씨", LocalDateTime.now()));

        //when
        ResultActions resultActions = mvc.perform(
                RestDocumentationRequestBuilders.get("/v1/search/rank")
        ).andDo(print());

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("list").exists())
                .andExpect(jsonPath("list").isArray())
                .andExpect(jsonPath("list[0].keyword").isString())
                .andExpect(jsonPath("list[0].count").isNumber())

                .andDo(document("searchKeyword_v1",
                        responseFields(
                                fieldWithPath("list").type(JsonFieldType.ARRAY).description("검색어 키워드 리스트"),
                                fieldWithPath("list[].keyword").type(JsonFieldType.STRING).description("검색어 키워드"),
                                fieldWithPath("list[].count").type(JsonFieldType.NUMBER).description("검색 횟수")
                        )
                ));


    }

    @DisplayName("/v2/search/rank v2 테스트")
    @Test
    public void searchTopTenKeyword_v2() throws Exception {
        //given
        //데이터가 없으므로 데이터를 밀어 넣어줌
        eventRedisService.saveEvent(new SearchEventMessage("날씨", LocalDateTime.now()));

        //when
        ResultActions resultActions = mvc.perform(
                RestDocumentationRequestBuilders.get("/v2/search/rank")
        ).andDo(print());

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("list").exists())
                .andExpect(jsonPath("list").isArray())
                .andExpect(jsonPath("list[0].keyword").isString())
                .andExpect(jsonPath("list[0].count").isNumber())

                .andDo(document("searchKeyword_v2",
                        responseFields(
                                fieldWithPath("list").type(JsonFieldType.ARRAY).description("검색어 키워드 리스트"),
                                fieldWithPath("list[].keyword").type(JsonFieldType.STRING).description("검색어 키워드"),
                                fieldWithPath("list[].count").type(JsonFieldType.NUMBER).description("검색 횟수")
                        )
                ));
    }
}
