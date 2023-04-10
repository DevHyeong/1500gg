package kr.gg.lol.web.usecase;

import kr.gg.lol.common.util.Rest;
import kr.gg.lol.common.util.Uri;
import kr.gg.lol.domain.match.dto.MatchDto;
import kr.gg.lol.domain.match.dto.ParticipantDto;
import kr.gg.lol.domain.match.entity.Ban;
import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.repository.MatchJdbcRepository;
import kr.gg.lol.domain.match.repository.MatchRepository;
import kr.gg.lol.domain.match.service.MatchService;
import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.entity.League;
import kr.gg.lol.domain.summoner.entity.Summoner;
import kr.gg.lol.domain.summoner.repository.SummonerJdbcRepository;
import kr.gg.lol.domain.summoner.repository.SummonerRepository;
import kr.gg.lol.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class MatchUpdateService {
    private final SummonerRepository summonerRepository;
    private final SummonerJdbcRepository summonerJdbcRepository;
    private final MatchJdbcRepository matchJdbcRepository;
    private final MatchRepository matchRepository;

    /**
     *  전적갱신
     *
     * */
    @Transactional
    public void updateMatches(String name){

        ResponseEntity<SummonerDto> summoner = Rest.get(Uri.summonerUri(name), SummonerDto.class);
        summonerRepository.save(new Summoner((summoner.getBody())));

        String id = summoner.getBody()
                .getId();
        ResponseEntity<List<LeagueDto>> leagues = Rest.get(Uri.leagueUri(id), new ParameterizedTypeReference<List<LeagueDto>>(){});
        summonerJdbcRepository.bulkInsert(
                leagues.getBody()
                        .stream()
                        .map(e-> new League(e))
                        .collect(Collectors.toList()));

        ResponseEntity<List<String>> matches = Rest.get(Uri.matchesUri(summoner.getBody().getPuuid()), new ParameterizedTypeReference<List<String>>() {});

        for(String matchId : matches.getBody()){
            ResponseEntity<MatchDto> res = Rest.get(Uri.matchInfoUri(matchId), MatchDto.class);
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
    }



}
