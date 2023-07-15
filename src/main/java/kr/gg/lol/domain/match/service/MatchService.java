package kr.gg.lol.domain.match.service;

import kr.gg.lol.common.util.RiotApi;
import kr.gg.lol.common.util.RiotURIGenerator;
import kr.gg.lol.domain.match.dto.*;
import kr.gg.lol.domain.match.entity.Ban;
import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.Team;
import kr.gg.lol.domain.match.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchJdbcRepository matchJdbcRepository;
    private final MatchRepository matchRepository;
    private final RiotApi riotApi;

    //@Cacheable(value = MATCHES, key = "#puuid")
    @Transactional
    public List<String> getMatchesByPuuid(String puuid){

        List<String> matches = matchRepository.findMatchesByPuuid(puuid);

        if(matches.size() < 1){
            return getMatchIdsAndSaveMatches(puuid);
        }

        return matches;
    }

    //@Cacheable(value = MATCH, key = "#matchId")
    @Transactional(readOnly = true)
    public List<MatchDto> getMatchesByIds(String... matchId){
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

        return matches.stream()
                        .map(m-> new MatchDto(m))
                        .collect(toList());
    }

    public List<String> getMatchIdsAndSaveMatches(String puuid){
        ResponseEntity<List<String>> matchIdsResponseEntity = riotApi.getWithToken(RiotURIGenerator.matchesUri(puuid), new ParameterizedTypeReference<List<String>>() {});

        for(String id : matchIdsResponseEntity.getBody()){
            ResponseEntity<MatchDto> matchDtoResponseEntity = riotApi.getWithToken(RiotURIGenerator.matchInfoUri(id), MatchDto.class);
            Match entity = new Match(matchDtoResponseEntity.getBody());
            matchJdbcRepository.saveWithoutRelation(entity);
            matchJdbcRepository.bulkInsertParticipants(entity.getParticipants());
            matchJdbcRepository.bulkInsertTeams(entity.getTeams());

            List<Ban> bans = new ArrayList<>();
            entity.getTeams().stream().forEach(e-> {
                bans.addAll(e.getBans());
            });

            matchJdbcRepository.bulkInsertBans(bans);

            matchDtoResponseEntity.getBody().getInfo().getTeams().forEach(t-> {
                t.setKillsChampion(sumKillsChampion(matchDtoResponseEntity.getBody().getInfo().getParticipants(), t.getTeamId()));
            });
        }
        return matchIdsResponseEntity.getBody();
    }


    public ResponseEntity getActiveMatchById(String id){

        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com")
                .path("/lol/spectator/v4/active-games/by-summoner/{id}")
                .encode()
                .build()
                .expand(id)
                .toUri();

        ResponseEntity<CurrentGameInfoDto> response = riotApi.getWithToken(uri, CurrentGameInfoDto.class);
        return response;
    }

    private int sumKillsChampion(List<ParticipantDto> participants, int teamId){
        return participants.stream()
                .filter(p-> p.getTeamId() == teamId)
                .mapToInt(ParticipantDto::getKills)
                .sum();
    }
}
