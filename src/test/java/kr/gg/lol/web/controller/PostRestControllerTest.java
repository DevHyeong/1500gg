package kr.gg.lol.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRestControllerTest {

    private MockMvc mockMvc;
    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testPosts() throws Exception{
        ResultActions result = mockMvc
                .perform(get("/api/posts?size=10"));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.length()", is(10)));
    }

    @Test
    void testPosts1() throws Exception{
        ResultActions result = mockMvc
                .perform(get("/api/posts?id=6&size=10"));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.length()", is(5)));
    }

}