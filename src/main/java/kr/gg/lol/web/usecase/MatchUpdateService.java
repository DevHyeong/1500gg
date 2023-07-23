package kr.gg.lol.web.usecase;

import kr.gg.lol.domain.summoner.exception.SummonerNotFoundException;
import kr.gg.lol.domain.match.service.MatchService;
import kr.gg.lol.domain.summoner.entity.Summoner;
import kr.gg.lol.domain.summoner.repository.SummonerRepository;
import kr.gg.lol.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class MatchUpdateService {
    private static final long LIMITED_MINUTES = 60;
    private final SummonerService summonerService;
    private final MatchService matchService;
    private final SummonerRepository summonerRepository;
    @Transactional
    public void updateMatches(String name){

        Summoner summoner = summonerRepository.findByName(name)
                .orElseThrow(() -> {
                    throw new SummonerNotFoundException();
                });

        if( Duration.between(summoner.getUpdatedAt(), LocalDateTime.now()).toMinutes() <= LIMITED_MINUTES){
            throw new IllegalStateException();
        }

        summonerService.saveSummoner(name);
        summonerService.saveLeague(summoner.getId());
        matchService.getMatchIdsAndSaveMatches(summoner.getPuuid());
    }
}
