package kr.gg.lol.web.usecase;

import kr.gg.lol.domain.match.service.MatchService;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.entity.Summoner;
import kr.gg.lol.domain.summoner.exception.SummonerNotFoundException;
import kr.gg.lol.domain.summoner.repository.SummonerRepository;
import kr.gg.lol.domain.summoner.service.SummonerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class MatchUpdateServiceTest {
    private MatchUpdateService service;
    @Mock
    private SummonerService summonerService;
    @Mock
    private MatchService matchService;
    @Mock
    private SummonerRepository summonerRepository;
    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        this.service = new MatchUpdateService(summonerService, matchService, summonerRepository);
    }

    @Test
    void updateMatchesWhenSummonerIsNotExistInDatabaseThenThrowRuntimeException() {
        //given
        String name = "서해 꿀주먹";
        given(summonerRepository.findByName(name)).willReturn(Optional.empty());

        //when, then
        assertThrowsExactly(SummonerNotFoundException.class, () -> service.updateMatches(name));
    }

    @Test
    void updateMatchesWhenUpdatedBeforeOneHoursThenThrowRuntimeException(){
        //given
        String name = "서해 꿀주먹";
        Summoner summoner = new Summoner();
        summoner.setUpdatedAt(LocalDateTime.now().plusMinutes(60));
        given(summonerRepository.findByName(name)).willReturn(Optional.of(summoner));

        //when, then
        assertThrowsExactly(IllegalStateException.class, () -> service.updateMatches(name));
    }

    @Test
    void updateMatchesWhenIsSuccessThenNotThrowExcetion(){
        //given
        String name = "서해 꿀주먹";
        Summoner summoner = new Summoner();
        summoner.setId("id");
        summoner.setPuuid("puuid");
        summoner.setUpdatedAt(LocalDateTime.now().plusMinutes(70));
        given(summonerRepository.findByName(name)).willReturn(Optional.of(summoner));
        given(summonerService.saveSummoner(name)).willReturn(new SummonerDto());
        given(summonerService.saveLeague(summoner.getId())).willReturn(new ArrayList<>());
        given(matchService.getMatchIdsAndSaveMatches(summoner.getPuuid())).willReturn(new ArrayList<>());

        //when, then
        assertDoesNotThrow(() -> service.updateMatches(name));
    }
}