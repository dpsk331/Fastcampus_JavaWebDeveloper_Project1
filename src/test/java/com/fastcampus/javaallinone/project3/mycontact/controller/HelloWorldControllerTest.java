package com.fastcampus.javaallinone.project3.mycontact.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HelloWorldControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }
    @Test
    void helloWorld() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloWorld"))
                .andDo(print())
                .andExpect(status().isOk())                                // status 가 200 인지 체크!
                .andExpect(MockMvcResultMatchers.content().string("HelloWorld"));   // response의 내용이 body : helloWorld 인지 체크!
    }

    @Test
    void helloException() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloException"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("알 수 없는 서버 오류가 발생하였습니다."));
    }

}