package kr.gg.lol.web.usecase;

import kr.gg.lol.domain.match.service.MatchService;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MatchUpdateService {
    private final SummonerService summonerService;
    private final MatchService matchService;

    @Transactional
    public void updateMatches(String name){
        SummonerDto summoner = summonerService.getSummonerByName(name, true);
        summonerService.getLeagueById(summoner.getId(), true);
        matchService.getMatchesByPuuid(summoner.getPuuid(), true);
    }

}
