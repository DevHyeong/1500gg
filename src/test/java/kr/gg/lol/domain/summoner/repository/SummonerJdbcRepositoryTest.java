package kr.gg.lol.domain.summoner.repository;

import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.entity.League;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SummonerJdbcRepositoryTest {

    @Autowired
    private SummonerJdbcRepository repository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Test
    void testBulkInsert(){
//        List<League> leagues = new ArrayList<>();
//        League league1 = League.builder()
//                .queueType("")
//                .summonerId("")
//                .summonerName("")
//                .leagueId("")
//                .rank("")
//                .tier("")
//                .wins(dto.getWins())
//                .losses(dto.getLosses())
//                .build();
//
//        League league2 = League.builder()
//                .queueType(dto.getQueueType())
//                .summonerId(dto.getSummonerId())
//                .summonerName(dto.getSummonerName())
//                .leagueId(dto.getLeagueId())
//                .rank(dto.getRank())
//                .tier(dto.getTier())
//                .wins(dto.getWins())
//                .losses(dto.getLosses())
//                .build();
//
//        leagues.add(league1);
//        leagues.add(league2);
//
//        repository.bulkInsert(leagues);
//
//        Optional<List<League>> result = leagueRepository.findBySummonerId("");


    }

}