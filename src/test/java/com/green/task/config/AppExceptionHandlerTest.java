package com.green.task.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AppExceptionHandlerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void noHandlerFoundTest() throws Exception {

        //given
        String noExistUrl = "/test/test";

        //when
        ResultActions resultActions = mvc.perform(
                get(noExistUrl)
        ).andDo(print());

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errReason").exists())
                .andExpect(jsonPath("errReason").isString())
                .andExpect(jsonPath("errCode", "APP_0004").exists())
                .andExpect(jsonPath("errCode").isString())
                .andExpect(jsonPath("errMsg", "NO_HANDLER_FOUND").exists())
                .andExpect(jsonPath("errMsg").isString());

    }


    @Test
    public void httpMethodNotSupportTest() throws Exception {

        //given
        String url = "/v1/blogs";

        //when
        ResultActions resultActions = mvc.perform(
                post(url)
        ).andDo(print());

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errReason").exists())
                .andExpect(jsonPath("errReason").isString())
                .andExpect(jsonPath("errCode", "APP_0003").exists())
                .andExpect(jsonPath("errCode").isString())
                .andExpect(jsonPath("errMsg", "METHOD_NOT_SUPPORT").exists())
                .andExpect(jsonPath("errMsg").isString());

    }

}
