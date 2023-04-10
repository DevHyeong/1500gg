package kr.gg.lol.common.util;

import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestTest {
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

        ResponseEntity<SummonerDto> result = Rest.get(uri, SummonerDto.class);
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    void testGetParameterizedTypeReference() throws Exception {
        final String id = "2pBOKMjspFzaFFfY6arc3I_sZ-xPWVbrtumRjHfQpwtS2_8";
        URI uri = Uri.leagueUri(id);
        ResponseEntity<List<LeagueDto>> result = Rest.get(uri, new ParameterizedTypeReference<List<LeagueDto>>() {});
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    void testExpected404() throws Exception{
        final String name = "fkdsjflurk,f";
        URI uri = Uri.summonerUri(name);
        assertThrows(RuntimeException.class, ()-> Rest.get(uri, SummonerDto.class));
    }

}