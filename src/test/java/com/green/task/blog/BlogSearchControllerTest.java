package com.green.task.blog;

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

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "http://localhost:8080")
public class BlogSearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("정상 blog search")
    @Test
    public void searchBlog() throws Exception {

        //given
        String query = "카카오";
        int page = 1;
        int size = 10;
        String sort = "accuracy";

        //when
        ResultActions resultActions = mvc.perform(
                RestDocumentationRequestBuilders.get("/v1/blogs")
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
        ).andDo(print());

        //then
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("list").exists())
                .andExpect(jsonPath("list").isArray())
                .andExpect(jsonPath("list[0].blogger").isString())
                .andExpect(jsonPath("list[0].contents").isString())
                .andExpect(jsonPath("list[0].time").isString())
                .andExpect(jsonPath("list[0].title").isString())
                .andExpect(jsonPath("list[0].url").isString())
                .andExpect(jsonPath("totalPages").exists())
                .andExpect(jsonPath("totalPages").isNumber())
                .andExpect(jsonPath("totalElements").exists())
                .andExpect(jsonPath("totalElements").isNumber())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("page").isNumber())
                .andExpect(jsonPath("size").exists())
                .andExpect(jsonPath("size").isNumber())
                .andExpect(jsonPath("last").exists())
                .andExpect(jsonPath("last").isBoolean())
                .andExpect(jsonPath("next").exists())
                .andExpect(jsonPath("next").isBoolean())
                .andExpect(jsonPath("sort").exists())
                .andExpect(jsonPath("sort").isString())

                .andDo(document("blog_search",
                        requestParameters(
                                parameterWithName("query").description("검색어 키워드"),
                                parameterWithName("page").description("default:1, 0보다 커야합니다."),
                                parameterWithName("size").description("default:10, 50이하만 허용"),
                                parameterWithName("sort").description("sort : accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy")
                        ),
                        responseFields(
                                fieldWithPath("list").type(JsonFieldType.ARRAY).description("블로그 검색 리스트"),
                                fieldWithPath("list[].blogger").type(JsonFieldType.STRING).description("블로그 블로거 네임"),
                                fieldWithPath("list[].contents").type(JsonFieldType.STRING).description("블로크 설명"),
                                fieldWithPath("list[].time").type(JsonFieldType.STRING).description("블로그 날짜"),
                                fieldWithPath("list[].title").type(JsonFieldType.STRING).description("블로그 제목"),
                                fieldWithPath("list[].url").type(JsonFieldType.STRING).description("블로그 link url"),
                                fieldWithPath("list[].thumbnail").type(JsonFieldType.STRING).description("블로그 썸네일이미지, 네이버 검색일 경우 데이터 없음").optional(),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("total page"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("total Elements"),
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("page, should be over 0"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size, should be under 50"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("페이지 마지막 유무"),
                                fieldWithPath("next").type(JsonFieldType.BOOLEAN).description("다음 페이지 유무"),
                                fieldWithPath("sort").type(JsonFieldType.STRING).description("sort : accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy")
                        )
                ));
    }

    @DisplayName("query string query is not exist then throw")
    @Test
    public void searchBlog_queryIsNull() throws Exception {

        //when
        ResultActions resultActions = mvc.perform(
                get("/v1/blogs")
        ).andDo(print());


        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errReason").exists())
                .andExpect(jsonPath("errReason").isString())
                .andExpect(jsonPath("errCode", "APP_0005").exists())
                .andExpect(jsonPath("errCode").isString())
                .andExpect(jsonPath("errMsg", "CHECK_QUERY_STRING").exists())
                .andExpect(jsonPath("errMsg").isString());
    }

    @DisplayName("query string page is 0 then throw")
    @Test
    public void searchBlog_pageIsZero() throws Exception {

        //given
        String query = "카카오";
        int page = 0;
        int size = 10;

        //when
        ResultActions resultActions = mvc.perform(
                get("/v1/blogs")
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
        ).andDo(print());


        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errReason").exists())
                .andExpect(jsonPath("errReason").isString())
                .andExpect(jsonPath("errCode", "APP_0005").exists())
                .andExpect(jsonPath("errCode").isString())
                .andExpect(jsonPath("errMsg", "CHECK_QUERY_STRING").exists())
                .andExpect(jsonPath("errMsg").isString());
    }

    @DisplayName("query string size is over 50 then throw")
    @Test
    public void searchBlog_sizeIsOver50() throws Exception {

        //given
        String query = "카카오";
        int page = 1;
        int size = 51;

        //when
        ResultActions resultActions = mvc.perform(
                get("/v1/blogs")
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
        ).andDo(print());


        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errReason").exists())
                .andExpect(jsonPath("errReason").isString())
                .andExpect(jsonPath("errCode", "APP_0005").exists())
                .andExpect(jsonPath("errCode").isString())
                .andExpect(jsonPath("errMsg", "CHECK_QUERY_STRING").exists())
                .andExpect(jsonPath("errMsg").isString());
    }

    @DisplayName("query string sort is invalid")
    @Test
    public void searchBlog_sortOptionIsInvalid() throws Exception {

        //given
        String query = "카카오";
        int page = 1;
        int size = 10;
        String sort = "invalid";

        //when
        ResultActions resultActions = mvc.perform(
                get("/v1/blogs")
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
        ).andDo(print());


        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errReason").exists())
                .andExpect(jsonPath("errReason").isString())
                .andExpect(jsonPath("errCode", "APP_0006").exists())
                .andExpect(jsonPath("errCode").isString())
                .andExpect(jsonPath("errMsg", "CHECK_QUERY_SORT_OPTION").exists())
                .andExpect(jsonPath("errMsg").isString());
    }
}
