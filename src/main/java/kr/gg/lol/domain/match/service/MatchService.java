package kr.gg.lol.domain.match.service;

import kr.gg.lol.common.util.Rest;
import kr.gg.lol.common.util.Uri;
import kr.gg.lol.domain.match.dto.*;
import kr.gg.lol.domain.match.entity.Ban;
import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.Team;
import kr.gg.lol.domain.match.repository.*;
import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
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
    @Transactional
    public ResponseEntity getMatchesByPuuid(String puuid){

        List<String> matches = matchRepository.findMatchesByPuuid(puuid);

        if(matches.size() < 20){
            ResponseEntity<List<String>> response = Rest.get(Uri.matchesUri(puuid), new ParameterizedTypeReference<List<String>>() {});

            for(String id : response.getBody()){
                ResponseEntity<MatchDto> res = Rest.get(Uri.matchInfoUri(id), MatchDto.class);
                Match entity = new Match(res.getBody());
                matchJdbcRepository.saveWithoutRelation(entity);
                matchJdbcRepository.bulkInsertParticipants(entity.getParticipants());
                matchJdbcRepository.bulkInsertTeams(entity.getTeams());

                List<Ban> bans = new ArrayList<>();
                entity.getTeams().stream().forEach(e-> {
                    bans.addAll(e.getBans());
                });

                matchJdbcRepository.bulkInsertBans(bans);

                res.getBody().getInfo().getTeams().forEach(t-> {

                    int kills = res.getBody().getInfo().getParticipants().stream().filter(p-> t.getTeamId() == p.getTeamId())
                            .mapToInt(ParticipantDto::getKills)
                            .sum();
                    t.setKillsChampion(kills);
                });
            }
            return response;
        }

        return ResponseEntity.ok(matches);

    }

    /**
     *   매치 상세정보
     *
     * */

    //@Cacheable(value = MATCH, key = "#matchId")
    @Transactional
    public ResponseEntity getMatchesByIds(String... matchId){
        List<Match> matches = matchRepository.findMatches(matchId);
        List<Participant> participants = matchRepository.findParticipantsByMatchIds(matchId);
        List<Team> teams = matchRepository.findTeamsByMatchIds(matchId);

        matches.forEach(e-> {
            e.setTeams(teams.stream()
                    .filter(t-> t.getMatchId().equals(e.getId()))
                    .collect(toList()));
            e.setParticipants(participants.stream()
                    .filter(p-> p.getMatchId().equals(e.getId()))
                    .collect(toList()));
        });

        return ResponseEntity.ok(
                matches.stream()
                        .map(m-> new MatchDto(m))
                        .collect(toList()));
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


}
