package kr.gg.lol.domain.match.repository;

import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MatchCustomRepositoryImplTest {

    @Autowired
    private MatchRepository matchRepository;
    @Test
    @Transactional
    void testFindMatches() {
        String[] matchId = {"KR_6430241097","KR_6438481695"};

        List<Match> matches = matchRepository.findMatches(matchId);
        List<Participant> participants = matchRepository.findParticipantsByMatchIds(matchId);
        List<Team> teams = matchRepository.findTeamsByMatchIds(matchId);

        matches.forEach(e-> {
            e.setTeams(teams.stream()
                    .filter(t-> t.getMatchId().equals(e.getId()))
                    .collect(toList()));
            e.setParticipants(participants.stream()
                    .filter(p-> p.getMatchId().equals(e.getId()))
                    .collect(toList()));
        });
        assertEquals(10, matches.get(0).getParticipants().size());
        assertEquals(2, matches.get(0).getTeams().size());
    }

    @Test
    void test(){
        String id = "";

        matchRepository.findById(id);



    }

    @Test
    void testfindByMatchIds(){
        List<Participant> participants = matchRepository.findParticipantsByMatchIds("KR_6430241097","KR_6438481695");
        participants.forEach(e-> System.out.println(e.getMatchId()));
    }
}