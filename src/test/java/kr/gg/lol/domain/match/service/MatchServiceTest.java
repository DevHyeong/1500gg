package kr.gg.lol.domain.match.service;

import kr.gg.lol.domain.match.dto.CurrentGameInfoDto;
import kr.gg.lol.domain.match.dto.MatchDto;
import kr.gg.lol.domain.match.repository.MatchJdbcRepository;
import kr.gg.lol.domain.match.repository.MatchRepository;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.service.SummonerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private MatchJdbcRepository matchJdbcRepository;

    @InjectMocks
    private MatchService matchService;


    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(matchService, "key", "RGAPI-4479766b-4ee2-4329-959b-826f8b1f9e72");
    }
    @Test
    public void testGetMatches() {
        final String puuId = "OgNhejISzksDuXDd7N1GW77219oMLvc1c6YTHwqXrv9u7vuirve6rQsMwMnu2yQ6YWkjrxpOPjZ2yg";
        doReturn(Optional.empty()).when(matchJdbcRepository).findMatchesByPuuid(puuId);
        ResponseEntity<List<String>> matches = matchService.getMatchesByPuuid(puuId);
        assertEquals(matches.getBody().size(), 20);

    }


    @Test
    public void testGetMatchByMatchId () {
        final String id = "KR_6274588740";
        doReturn(Optional.empty()).when(matchRepository).findById(id);
        ResponseEntity<MatchDto> match = matchService.getMatchByMatchId(id);

    }

    @Test
    public void 현재진행중인게임(){
      /*  ResponseEntity<SummonerDto> summoner = summonerService.getSummonerByName("서해 꿀주먹");
        ResponseEntity<CurrentGameInfoDto> currentGameInfo = matchService.getActiveMatchById(summoner.getBody().getId());

        currentGameInfo.getBody().getBannedChampions()
                .forEach(e-> System.out.println(e.getChampionId()));*/

    }

}