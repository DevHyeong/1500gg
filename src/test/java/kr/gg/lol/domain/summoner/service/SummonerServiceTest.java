package kr.gg.lol.domain.summoner.service;

import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.entity.Summoner;
import kr.gg.lol.domain.summoner.repository.LeagueRepository;
import kr.gg.lol.domain.summoner.repository.SummonerJdbcRepository;
import kr.gg.lol.domain.summoner.repository.SummonerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SummonerServiceTest {

    @InjectMocks
    private SummonerService summonerService;

    @Mock
    private SummonerRepository summonerRepository;
    @Mock
    private SummonerJdbcRepository summonerJdbcRepository;
    @Mock
    private LeagueRepository leagueRepository;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(summonerService, "key", "RGAPI-4479766b-4ee2-4329-959b-826f8b1f9e72");
    }

    @Test
    void testGetSummonerByName() throws Exception{
        final String name = "Hide on bush";

        doReturn(Optional.empty()).when(summonerRepository).findByName(name);
        ResponseEntity<SummonerDto> result = summonerService.getSummonerByName(name);

        assertEquals(result.getStatusCodeValue(), 200);
        assertEquals(result.getBody().getName(), name);

    }

    @Test
    @DisplayName("존재하지 않는 소환사를 검색했을 때")
    void notExistSummoner() throws Exception{
        final String name = "ㅇ라ㅣ헐이ㅏ헢";

        doReturn(Optional.empty()).when(summonerRepository).findByName(name);
        ResponseEntity<SummonerDto> result = summonerService.getSummonerByName(name);
        assertEquals(result.getStatusCodeValue(), 404);

    }

    @Test
    void testGetLeagueInfo() throws Exception{
        final String id = "uW5Ras1Ap73QVN7mUz5CVGQUqOJSTg1bhagOKpBr11Os3w";
        doReturn(Optional.empty()).when(leagueRepository).findBySummonerId(id);
        ResponseEntity<List<LeagueDto>> result = summonerService.getLeagueById(id);

        result.getBody()
                .stream().forEach(e-> {
                    assertEquals(e.getSummonerName(), "Hide on bush");
                });
    }

    @Test
    void testNotExistLeagueInfo() throws Exception{

        final String id = "uW5Ras1Ap73QVN7mUz5CVGQUqOJSTg1bhagOKpBr11Os3w";
        doReturn(Optional.empty()).when(leagueRepository).findBySummonerId(id);
        ResponseEntity<List<LeagueDto>> result = summonerService.getLeagueById(id);

        result.getBody()
                .stream().forEach(e-> {
                    assertEquals(e.getSummonerName(), "Hide on bush");
                });

    }



}