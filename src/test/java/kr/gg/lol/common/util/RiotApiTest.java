package kr.gg.lol.common.util;

import kr.gg.lol.common.ConfigFileProperties;
import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RiotApiTest {

    private RiotApi riotApi;
    @BeforeEach
    void setUp() {
        riotApi = new RiotApi(new ConfigFileProperties());
    }

    @Test
    void testGetSummoner() throws Exception {
        final String name = "Hide on bush";
        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com")
                .path("/lol/summoner/v4/summoners/by-name/{name}")
                .encode()
                .build()
                .expand(name)
                .toUri();

        ResponseEntity<SummonerDto> result = riotApi.getWithToken(uri, SummonerDto.class);
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    void testGetParameterizedTypeReference() throws Exception {
        final String id = "2pBOKMjspFzaFFfY6arc3I_sZ-xPWVbrtumRjHfQpwtS2_8";
        URI uri = RiotURIGenerator.leagueUri(id);
        ResponseEntity<List<LeagueDto>> result = riotApi.getWithToken(uri, new ParameterizedTypeReference<List<LeagueDto>>() {});
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    void testExpected404() throws Exception{
        final String name = "fkdsjflurk,f";
        URI uri = RiotURIGenerator.summonerUri(name);
        assertThrows(RuntimeException.class, ()-> riotApi.getWithToken(uri, SummonerDto.class));
    }

}