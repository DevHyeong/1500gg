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
    @Test
    public void testGetMatches() {
        final String puuId = "OgNhejISzksDuXDd7N1GW77219oMLvc1c6YTHwqXrv9u7vuirve6rQsMwMnu2yQ6YWkjrxpOPjZ2yg";
        doReturn(Optional.empty()).when(matchJdbcRepository).findMatchesByPuuid(puuId);
        ResponseEntity<List<String>> matches = matchService.getMatchesByPuuid(puuId);
        assertEquals( 20, matches.getBody().size());
    }


    @Test
    public void testGetMatchByMatchId () {
        final String id = "KR_6274588740";
        doReturn(Optional.empty()).when(matchRepository).findById(id);
        ResponseEntity<MatchDto> match = matchService.getMatchByMatchId(id);
        assertEquals(match.getBody().getMetadata().getMatchId(), id);
    }

}