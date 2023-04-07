package kr.gg.lol.domain.match.service;

import kr.gg.lol.common.util.Rest;
import kr.gg.lol.domain.match.dto.*;
import kr.gg.lol.domain.match.entity.Ban;
import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.Team;
import kr.gg.lol.domain.match.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static kr.gg.lol.common.constant.CacheConstants.MATCH;
import static kr.gg.lol.common.constant.CacheConstants.MATCHES;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchJdbcRepository matchJdbcRepository;
    private final MatchRepository matchRepository;

    /**
     *   매치 리스트
     *
     * */
    @Cacheable(value = MATCHES, key = "#puuid")
    public ResponseEntity getMatchesByPuuid(String puuid){

        Optional<List<String>> matches = matchJdbcRepository.findMatchesByPuuid(puuid);

        if(matches.isEmpty()){

            URI uri = UriComponentsBuilder
                    .fromUriString("https://asia.api.riotgames.com")
                    .path("/lol/match/v5/matches/by-puuid/{puuid}/ids")
                    .encode()
                    .build()
                    .expand(puuid)
                    .toUri();

            ResponseEntity<List<String>> response = Rest.get(uri, new ParameterizedTypeReference<List<String>>() {});
            matchJdbcRepository.bulkInsertMatches(puuid, response.getBody());




            return response;
        }

        return ResponseEntity.ok(matches.get());

    }

    /**
     *   매치 상세정보
     *
     * */

    @Cacheable(value = MATCH, key = "#matchId")
    @Transactional
    public ResponseEntity getMatchByMatchId(String matchId){
        Optional<Match> match = matchRepository.findById(matchId);
        if(match.isEmpty()){
            URI uri = UriComponentsBuilder
                    .fromUriString("https://asia.api.riotgames.com")
                    .path("/lol/match/v5/matches/{matchId}")
                    .encode()
                    .build()
                    .expand(matchId)
                    .toUri();
            ResponseEntity<MatchDto> response = Rest.get(uri, MatchDto.class);
            //saveMatch(response.getBody());
            Match entity = new Match(response.getBody());
            matchJdbcRepository.saveWithoutRelation(entity);
            matchJdbcRepository.bulkInsertParticipants(entity.getParticipants());
            matchJdbcRepository.bulkInsertTeams(entity.getTeams());

            List<Ban> bans = new ArrayList<>();
            entity.getTeams().stream().forEach(e-> {
                bans.addAll(e.getBans());
            });

            matchJdbcRepository.bulkInsertBans(bans);

            response.getBody().getInfo().getTeams().forEach(t-> {

                int kills = response.getBody().getInfo().getParticipants().stream().filter(p-> t.getTeamId() == p.getTeamId())
                        .mapToInt(ParticipantDto::getKills)
                        .sum();
                t.setKillsChampion(kills);
            });

            return response;
        }
        return ResponseEntity.ok(new MatchDto(match.get()));

    }

    /**
     *  현재 게임중 정보
     *
     * */
    public ResponseEntity getActiveMatchById(String id){

        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com")
                .path("/lol/spectator/v4/active-games/by-summoner/{id}")
                .encode()
                .build()
                .expand(id)
                .toUri();

        ResponseEntity<CurrentGameInfoDto> response = Rest.get(uri, CurrentGameInfoDto.class);
        return response;
    }

    private void saveMatch(MatchDto dto){
        Match match = new Match(dto);
        matchJdbcRepository.saveWithoutRelation(match);
        matchJdbcRepository.bulkInsertParticipants(match.getParticipants());
        matchJdbcRepository.bulkInsertTeams(match.getTeams());

        List<Ban> bans = new ArrayList<>();
        match.getTeams().stream().forEach(e-> {
            bans.addAll(e.getBans());
        });

        matchJdbcRepository.bulkInsertBans(bans);

        dto.getInfo().getTeams().forEach(t-> {

            int kills = dto.getInfo().getParticipants().stream().filter(p-> t.getTeamId() == p.getTeamId())
                    .mapToInt(ParticipantDto::getKills)
                    .sum();
            t.setKillsChampion(kills);
        });

    }

}
