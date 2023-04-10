package kr.gg.lol.domain.match.repository;

import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.Team;

import java.util.List;

public interface MatchCustomRepository {
    List<String> findMatchesByPuuid(String puuid);
    List<Match> findMatches(String... matchId);
    List<Participant> findParticipantsByMatchIds(String... matchId);
    List<Team> findTeamsByMatchIds(String... matchId);
}
