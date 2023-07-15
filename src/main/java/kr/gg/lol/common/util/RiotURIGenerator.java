package kr.gg.lol.common.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class RiotURIGenerator {

    private static final String KR_API_URL = "https://kr.api.riotgames.com";
    private static final String ASIA_API_URL = "https://asia.api.riotgames.com";

    public static URI leagueUri(String id){
        return UriComponentsBuilder
                .fromUriString(KR_API_URL)
                .path("/lol/league/v4/entries/by-summoner/{id}")
                .encode()
                .build()
                .expand(id)
                .toUri();
    }

    public static URI summonerUri(String name){
        return UriComponentsBuilder
                .fromUriString(KR_API_URL)
                .path("/lol/summoner/v4/summoners/by-name/{name}")
                .encode()
                .build()
                .expand(name)
                .toUri();
    }

    public static URI matchesUri(String puuid){
        return UriComponentsBuilder
                .fromUriString(ASIA_API_URL)
                .path("/lol/match/v5/matches/by-puuid/{puuid}/ids")
                .encode()
                .build()
                .expand(puuid)
                .toUri();
    }

    public static URI matchInfoUri(String matchId){
        return UriComponentsBuilder
                .fromUriString(ASIA_API_URL)
                .path("/lol/match/v5/matches/{matchId}")
                .encode()
                .build()
                .expand(matchId)
                .toUri();
    }
}
