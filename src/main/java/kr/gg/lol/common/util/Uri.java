package kr.gg.lol.common.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class Uri {

    public static URI leagueUri(String id){
        return UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com")
                .path("/lol/league/v4/entries/by-summoner/{id}")
                .encode()
                .build()
                .expand(id)
                .toUri();
    }

    public static URI summonerUri(String name){
        return UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com")
                .path("/lol/summoner/v4/summoners/by-name/{name}")
                .encode()
                .build()
                .expand(name)
                .toUri();
    }

    public static URI matchesUri(String puuid){
        return UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com")
                .path("/lol/match/v5/matches/by-puuid/{puuid}/ids")
                .encode()
                .build()
                .expand(puuid)
                .toUri();
    }

    public static URI matchInfoUri(String matchId){
        return UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com")
                .path("/lol/match/v5/matches/{matchId}")
                .encode()
                .build()
                .expand(matchId)
                .toUri();
    }


}
