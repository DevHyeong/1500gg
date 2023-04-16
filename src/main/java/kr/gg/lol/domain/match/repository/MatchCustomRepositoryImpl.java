package kr.gg.lol.domain.match.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.Team;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.gg.lol.domain.match.entity.QBan.ban;
import static kr.gg.lol.domain.match.entity.QMatch.match;
import static kr.gg.lol.domain.match.entity.QParticipant.participant;
import static kr.gg.lol.domain.match.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class MatchCustomRepositoryImpl implements MatchCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<String> findMatchesByPuuid(String puuid) {
        return jpaQueryFactory.select(match.id)
                .from(match)
                .leftJoin(participant).on(match.id.eq(participant.matchId))
                .fetchJoin()
                .where(participant.puuid.eq(puuid))
                .orderBy(match.gameStartTime.desc())
                .limit(20)
                .fetch();
    }

    @Override
    public List<Match> findMatches(String... matchId) {
        List<Match> matches = jpaQueryFactory.selectFrom(match)
                .where(match.id.in(matchId))
                .orderBy(match.gameStartTime.desc())
                .fetch();
        return matches;
    }
    @Override
    public List<Participant> findParticipantsByMatchIds(String... matchId) {
        return jpaQueryFactory.selectFrom(participant)
                .join(participant.perkPrimary)
                .fetchJoin()
                .join(participant.perkSub)
                .fetchJoin()
                .where(participant.matchId.in(matchId))
                .fetch();
    }

    @Override
    public List<Team> findTeamsByMatchIds(String... matchId) {
       List<Team> result = jpaQueryFactory.selectFrom(team)
               .distinct()
               .leftJoin(team.bans, ban)
                .fetchJoin()
                .where(team.matchId.in(matchId))
                .fetch();
       return result;
    }
}
