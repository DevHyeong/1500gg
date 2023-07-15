package kr.gg.lol.domain.summoner.service;

import kr.gg.lol.common.util.RiotApi;
import kr.gg.lol.common.util.RiotURIGenerator;
import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.entity.League;
import kr.gg.lol.domain.summoner.entity.Summoner;
import kr.gg.lol.domain.summoner.repository.LeagueRepository;
import kr.gg.lol.domain.summoner.repository.SummonerJdbcRepository;
import kr.gg.lol.domain.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static kr.gg.lol.domain.summoner.dto.LeagueDto.toDtos;


@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerRepository summonerRepository;
    private final SummonerJdbcRepository summonerJdbcRepository;
    private final LeagueRepository leagueRepository;
    private final RiotApi riotApi;

    //@Cacheable(value = SUMMONER_NAME, key = "#name")
    @Transactional
    public SummonerDto getSummonerByName(String name){
        Optional<Summoner> summoner = summonerRepository.findByName(name);

        if(summoner.isEmpty()){
            return saveSummoner(name);
        }

        return new SummonerDto(summoner.get());
    }

    //@Cacheable(value = LEAGUE_ID, key = "#id")
    public List<LeagueDto> getLeagueById(String id){
        Optional<List<League>> leagues = leagueRepository.findBySummonerId(id);

        if(leagues.isEmpty() || leagues.get().size() < 1){
            return saveLeague(id);
        }

        return toDtos(leagues.get());
    }

    public SummonerDto saveSummoner(String name){
        ResponseEntity<SummonerDto> response = riotApi.getWithToken(RiotURIGenerator.summonerUri(name), SummonerDto.class);
        Summoner entity = new Summoner(response.getBody());
        Summoner result = summonerRepository.save(entity);
        return new SummonerDto(result);
    }

    public List<LeagueDto> saveLeague(String id){
        ResponseEntity<List<LeagueDto>> response = riotApi.getWithToken(RiotURIGenerator.leagueUri(id), new ParameterizedTypeReference<List<LeagueDto>>() {});
        summonerJdbcRepository.bulkInsert(
                response.getBody()
                        .stream()
                        .map(e-> new League(e))
                        .collect(Collectors.toList()));
        return toDtos(leagueRepository.findBySummonerId(id).get());
    }

}
