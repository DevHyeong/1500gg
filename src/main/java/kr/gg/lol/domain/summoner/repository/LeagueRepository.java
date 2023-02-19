package kr.gg.lol.domain.summoner.repository;

import kr.gg.lol.domain.summoner.entity.League;
import kr.gg.lol.domain.summoner.entity.pk.LeaguePk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, LeaguePk> {

    Optional<List<League>> findBySummonerId(String summonerId);

}
