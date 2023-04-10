package kr.gg.lol.domain.match.repository;

import kr.gg.lol.domain.match.entity.Match;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MatchRepositoryTest {
    @Autowired
    private MatchRepository matchRepository;
    @Test
    void testFindMatchesByPuuid(){
        final String puuid = "i2xfv9hiybuQxfRGxkHHq5-q8sxLopkLdPoZ1zWRB6a0pmVVVcMhFK5xdmXdyB4KYI08td3BiDnbAw";
        List<String> matches = matchRepository.findMatchesByPuuid(puuid);
        assertEquals(20, matches.size());
    }




}