package kr.gg.lol.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gg.lol.domain.match.dto.RequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ActiveProfiles("local")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchControllerTest {
    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    @Test
    void testMatchesByPuuId() throws Exception{
        String puuid = "i2xfv9hiybuQxfRGxkHHq5-q8sxLopkLdPoZ1zWRB6a0pmVVVcMhFK5xdmXdyB4KYI08td3BiDnbAw";
        ResultActions result = mockMvc.perform(get(String.format("/api/matches/%s", puuid))
                .accept(MediaType.APPLICATION_JSON));

        result.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.length()", is(20)));
    }

    @Test
    void testMatches() throws Exception{
        RequestDto requestDto = new RequestDto();
        requestDto.setIds(List.of("KR_6429856682","KR_6431652356","KR_6433805001","KR_6433984483",
                "KR_6435359171","KR_6439687377","KR_6424854720","KR_6424933780",
                "KR_6424976155","KR_6430241097","KR_6431096745","KR_6438481695",
                "KR_6433013903","KR_6433636856","KR_6436405053","KR_6437213323",
                "KR_6438402401","KR_6424892660","KR_6425008794","KR_6431838325"));

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(requestDto);
        ResultActions result = mockMvc.perform(post("/api/v1/matches")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        result.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.length()", is(20)))
            .andExpect(jsonPath("$.body[0].metadata.matchId", is("KR_6439687377")))
            .andExpect(jsonPath("$.body[0].info.participants.length()", is(10)))
            .andExpect(jsonPath("$.body[0].info.teams.length()", is(2)));

    }

//    @Test
//    @DisplayName("전적 갱신")
//    void testRenewal() throws Exception{
//
//        String content = "{name: \"팀운 그 잡채\"}";
//        ResultActions result = mockMvc.perform(post("/api/matches/renewal")
//                .content(content)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON));
//        result.andDo(print())
//                .andExpect(status().is5xxServerError());
//    }



}
