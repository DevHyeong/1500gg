package kr.gg.lol.domain.summoner.usecase;

import kr.gg.lol.web.usecase.MatchUpdateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MatchUpdateServiceTest {

    @Autowired
    MatchUpdateService matchUpdateService;

    @Test
    void test1() {

        matchUpdateService.test("서해 꿀주먹");

    }
}