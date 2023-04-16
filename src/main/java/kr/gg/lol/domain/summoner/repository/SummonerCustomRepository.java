package kr.gg.lol.domain.summoner.repository;

import kr.gg.lol.domain.summoner.entity.Summoner;

import java.util.Optional;

public interface SummonerCustomRepository {
    Optional<Summoner> findByName(String name);
}
