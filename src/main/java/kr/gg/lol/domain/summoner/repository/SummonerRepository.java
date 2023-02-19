package kr.gg.lol.domain.summoner.repository;

import kr.gg.lol.domain.summoner.entity.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, Long> {

    Optional<Summoner> findByName(String name);

}
