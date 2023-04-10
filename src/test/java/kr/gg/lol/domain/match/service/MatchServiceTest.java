package kr.gg.lol.domain.match.service;

import kr.gg.lol.domain.match.dto.MatchDto;
import kr.gg.lol.domain.match.repository.MatchJdbcRepository;
import kr.gg.lol.domain.match.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;


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
        //doReturn(Optional.empty()).when(matchRepository).findById(id);
        String[] id = {"KR_6424854720", "KR_6424892660", "KR_6424933780", "KR_6424976155",
                "KR_6425008794", "KR_6429856682", "KR_6430241097", "KR_6431096745", "KR_6431652356",
                "KR_6431838325", "KR_6433013903", "KR_6433636856", "KR_6433805001", "KR_6433984483",
                "KR_6435359171", "KR_6436405053", "KR_6437213323", "KR_6437312271", "KR_6438402401",
                "KR_6438481695"};

        ResponseEntity<List<MatchDto>> response = matchService.getMatchesByIds(id);
        //assertEquals(response.getBody().getMetadata().getMatchId(), id);
    }
}