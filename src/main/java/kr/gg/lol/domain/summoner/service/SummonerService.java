package kr.gg.lol.domain.summoner.service;

import kr.gg.lol.common.util.Rest;
import kr.gg.lol.common.util.Uri;
import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.entity.League;
import kr.gg.lol.domain.summoner.entity.Summoner;
import kr.gg.lol.domain.summoner.repository.LeagueRepository;
import kr.gg.lol.domain.summoner.repository.SummonerJdbcRepository;
import kr.gg.lol.domain.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.gg.lol.common.constant.CacheConstants.LEAGUE_ID;
import static kr.gg.lol.common.constant.CacheConstants.SUMMONER_NAME;
import static kr.gg.lol.common.util.Preconditions.checkNotNull;
import static kr.gg.lol.domain.summoner.dto.LeagueDto.toDto;


@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerRepository summonerRepository;
    private final SummonerJdbcRepository summonerJdbcRepository;
    private final LeagueRepository leagueRepository;

    private final Rest rest;

    //@Cacheable(value = SUMMONER_NAME, key = "#name")
    @Transactional
    public SummonerDto getSummonerByName(String name, boolean renewal){

        Optional<Summoner> summoner = summonerRepository.findByName(name);
        System.out.println(name);
        if(renewal || summoner.isEmpty()){
            ResponseEntity<SummonerDto> response = rest.get(Uri.summonerUri(name), SummonerDto.class);
            Summoner entity = new Summoner(response.getBody());
            summonerRepository.save(entity);
            return response.getBody();
        }
        return new SummonerDto(summoner.get());
    }

    //@Cacheable(value = LEAGUE_ID, key = "#id")
    public List<LeagueDto> getLeagueById(String id, boolean renewal){
        Optional<List<League>> leagues = leagueRepository.findBySummonerId(id);
        if(renewal || leagues.isEmpty() || leagues.get().size() < 1){
            ResponseEntity<List<LeagueDto>> response = rest.get(Uri.leagueUri(id), new ParameterizedTypeReference<List<LeagueDto>>() {});
            summonerJdbcRepository.bulkInsert(
                    response.getBody()
                    .stream()
                    .map(e-> new League(e))
                    .collect(Collectors.toList()));
            return response.getBody();
        }
        return toDto(leagues.get());
    }

}
