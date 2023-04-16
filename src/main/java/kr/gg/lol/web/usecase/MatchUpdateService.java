package kr.gg.lol.web.usecase;

import kr.gg.lol.common.util.Rest;
import kr.gg.lol.common.util.Uri;
import kr.gg.lol.domain.match.dto.MatchDto;
import kr.gg.lol.domain.match.dto.ParticipantDto;
import kr.gg.lol.domain.match.entity.Ban;
import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.entity.Participant;
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
    private final SummonerService summonerService;
    private final MatchService matchService;

    /**
     *  전적갱신
     *
     * */
    @Transactional
    public void updateMatches(String name){
        SummonerDto summoner = summonerService.getSummonerByName(name, true);
        summonerService.getLeagueById(summoner.getId(), true);
        matchService.getMatchesByPuuid(summoner.getPuuid(), true);
    }

}
