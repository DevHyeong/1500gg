package kr.gg.lol.domain.match.repository;

import kr.gg.lol.domain.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, String>, MatchCustomRepository {
}
