package kr.gg.lol.domain.summoner.repository;

import kr.gg.lol.domain.summoner.entity.Summoner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SummonerRepositoryTest {

    @Autowired
    private SummonerRepository summonerRepository;

    @Test
    void 소환사정보_저장 () throws Exception{

        Summoner summoner = Summoner.builder()
                .name("Hide on bush")
                .puuid("OgNhejISzksDuXDd7N1GW77219oMLvc1c6YTHwqXrv9u7vuirve6rQsMwMnu2yQ6YWkjrxpOPjZ2yg")
                .id("uW5Ras1Ap73QVN7mUz5CVGQUqOJSTg1bhagOKpBr11Os3w")
                .accountId("VDu8SDnJ7l0h1cMHH03V4Qd5QHgro7t_52ismld6X_Yk")
                .profileIconId(6)
                .summonerLevel(615)
                .build();

        final Summoner result = summonerRepository.save(summoner);

        assertNotNull(result);
        assertEquals(result.getName(), "Hide on bush");
        assertEquals(result.getPuuid(), "OgNhejISzksDuXDd7N1GW77219oMLvc1c6YTHwqXrv9u7vuirve6rQsMwMnu2yQ6YWkjrxpOPjZ2yg");
        assertEquals(result.getId(), "uW5Ras1Ap73QVN7mUz5CVGQUqOJSTg1bhagOKpBr11Os3w");
        assertEquals(result.getAccountId(), "VDu8SDnJ7l0h1cMHH03V4Qd5QHgro7t_52ismld6X_Yk");
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void 소환사정보_가져오기() throws Exception{
        Summoner summoner = Summoner.builder()
                .name("Hide on bush")
                .puuid("OgNhejISzksDuXDd7N1GW77219oMLvc1c6YTHwqXrv9u7vuirve6rQsMwMnu2yQ6YWkjrxpOPjZ2yg")
                .id("uW5Ras1Ap73QVN7mUz5CVGQUqOJSTg1bhagOKpBr11Os3w")
                .accountId("VDu8SDnJ7l0h1cMHH03V4Qd5QHgro7t_52ismld6X_Yk")
                .profileIconId(6)
                .summonerLevel(615)
                .build();

        summonerRepository.save(summoner);
        final Summoner result = summonerRepository.findByName("Hide on bush")
                .orElseThrow(()-> new RuntimeException());

        assertNotNull(result);
        assertEquals(result.getName(), "Hide on bush");
        assertEquals(result.getPuuid(), "OgNhejISzksDuXDd7N1GW77219oMLvc1c6YTHwqXrv9u7vuirve6rQsMwMnu2yQ6YWkjrxpOPjZ2yg");
        assertEquals(result.getId(), "uW5Ras1Ap73QVN7mUz5CVGQUqOJSTg1bhagOKpBr11Os3w");
        assertEquals(result.getAccountId(), "VDu8SDnJ7l0h1cMHH03V4Qd5QHgro7t_52ismld6X_Yk");
    }
}