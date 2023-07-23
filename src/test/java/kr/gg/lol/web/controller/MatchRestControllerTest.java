package kr.gg.lol.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gg.lol.domain.match.dto.RequestDto;
import org.json.JSONObject;
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
public class MatchRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testMatchesByPuuIdWhenMatchesAreExistInDatabase() throws Exception{
        //given
        String puuid = "i2xfv9hiybuQxfRGxkHHq5-q8sxLopkLdPoZ1zWRB6a0pmVVVcMhFK5xdmXdyB4KYI08td3BiDnbAw";

        //when
        ResultActions result = mockMvc.perform(get(String.format("/api/matches/%s", puuid))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.length()", is(20)));
    }

    @Test
    @DisplayName("riot api 영향을 받을 수 있음")
    void testMatchesByPuuIdWhenMatchesAreNotExistInDatabase() throws Exception{
        //given
        String puuid = "w9gMe7y7Oq13dHgiUfwsVAnWMxdqd6uRf86TncAJYb4ZlV9sxvroghYy6mPcQPERkZXDa2hnYa4vbg";

        //when
        ResultActions result = mockMvc.perform(get(String.format("/api/matches/%s", puuid))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.length()", is(20)));
    }
    @Test
    void testMatchesWhenMatchesAreExistInDatabase() throws Exception{
        RequestDto requestDto = new RequestDto();
        requestDto.setIds(List.of("KR_6429856682","KR_6431652356","KR_6433805001","KR_6433984483",
                "KR_6435359171","KR_6439687377","KR_6424854720","KR_6424933780",
                "KR_6424976155","KR_6430241097","KR_6431096745","KR_6438481695",
                "KR_6433013903","KR_6433636856","KR_6436405053","KR_6437213323",
                "KR_6438402401","KR_6424892660","KR_6425008794","KR_6431838325"));

        String content = this.objectMapper.writeValueAsString(requestDto);
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
    @Test
    void testRenewalWhenSummonerIsNotExistThenReturnForbidden() throws Exception{

        String content = "{\"name\": \"팀운 그 잡채12\"}";
        ResultActions result = mockMvc.perform(post("/api/matches/renewal")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        result.andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void testRenewalWhenRecentlyUpdatedThenReturnForbidden() throws Exception{

        String content = "{\"name\": \"팀운 그 잡채\"}";
        mockMvc.perform(post("/api/matches/renewal")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        Thread.sleep(5000);

        ResultActions result = mockMvc.perform(post("/api/matches/renewal")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        result.andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("riot api 영향을 받을 수 있음")
    void testRenewalWhenIsSuccessThenReturnOk() throws Exception{
        String content = "{\"name\": \"팀운 그 잡채\"}";
        ResultActions result = mockMvc.perform(post("/api/matches/renewal")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        result.andDo(print())
                .andExpect(status().isOk());
    }
}
