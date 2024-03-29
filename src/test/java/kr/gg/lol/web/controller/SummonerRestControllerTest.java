package kr.gg.lol.web.controller;

import org.junit.jupiter.api.DisplayName;
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
public class SummonerRestControllerTest {

    private MockMvc mockMvc;
    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testExistSummonerByName() throws Exception{
        ResultActions result = mockMvc.perform(get("/api/summoner/팀운 그 잡채"));
        result.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.name",is("팀운 그 잡채")))
            .andExpect(jsonPath("$.body.id", is("2pBOKMjspFzaFFfY6arc3I_sZ-xPWVbrtumRjHfQpwtS2_8")))
            .andExpect(jsonPath("$.body.puuid", is("i2xfv9hiybuQxfRGxkHHq5-q8sxLopkLdPoZ1zWRB6a0pmVVVcMhFK5xdmXdyB4KYI08td3BiDnbAw")))
            .andExpect(jsonPath("$.body.accountId", is("QsiZS9Qfgm-3-XyNtbUN0Q6sISfQouNMNPsGNN2IduPe8j4")));
    }

    @Test
    @DisplayName("riot api 영향을 받을 수 있음")
    void testNotExistSummonerByName() throws Exception{
        ResultActions result = mockMvc.perform((get("/api/summoner/밀감충")));
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.name", is("밀감충")))
                .andExpect(jsonPath("$.body.accountId", is("KpQEv_pV84S9sXD5AoFaTXcs3eBYcB-Oi7N3keOprRqzHiTttQmbktjr")))
                .andExpect(jsonPath("$.body.id", is("Q6Ie60wg4cQaEAwT5zSWq99ncrUHYy1wQeFUEH1jOF__V_U")))
                .andExpect(jsonPath("$.body.puuid", is("w9gMe7y7Oq13dHgiUfwsVAnWMxdqd6uRf86TncAJYb4ZlV9sxvroghYy6mPcQPERkZXDa2hnYa4vbg")));
    }

    @Test
    void testExistLeagueById() throws Exception{
        ResultActions result = mockMvc.perform(get("/api/league/2pBOKMjspFzaFFfY6arc3I_sZ-xPWVbrtumRjHfQpwtS2_8"));
        result.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body[0].summonerId", is("2pBOKMjspFzaFFfY6arc3I_sZ-xPWVbrtumRjHfQpwtS2_8")))
            .andExpect(jsonPath("$.body[0].queueType", is("RANKED_SOLO_5x5")))
            .andExpect(jsonPath("$.body[0].summonerName", is("팀운 그 잡채")));
    }

    @Test
    @DisplayName("riot api 영향을 받을 수 있음")
    void testNotExistLeagueById() throws Exception{
        ResultActions result = mockMvc.perform(get("/api/league/Q6Ie60wg4cQaEAwT5zSWq99ncrUHYy1wQeFUEH1jOF__V_U"));
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body[0].summonerId", is("Q6Ie60wg4cQaEAwT5zSWq99ncrUHYy1wQeFUEH1jOF__V_U")))
                .andExpect(jsonPath("$.body[0].queueType", is("RANKED_SOLO_5x5")))
                .andExpect(jsonPath("$.body[0].summonerName", is("밀감충")));
    }

}
