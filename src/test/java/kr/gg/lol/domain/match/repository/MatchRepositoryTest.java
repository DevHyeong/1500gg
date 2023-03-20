package kr.gg.lol.domain.match.repository;

import kr.gg.lol.domain.match.entity.Match;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;


@DataJpaTest
class MatchRepositoryTest {
    @Autowired
    MatchRepository matchRepository;

    @Test
    void select_Test(){
        Optional<Match> match = matchRepository.findById("KR_6271300313");

        //match.get().getParticipants().forEach(e-> System.out.println(e.getSummonerName()));


        match.get().getTeams().forEach(e-> System.out.println(e.getMatchId()));


    }

}