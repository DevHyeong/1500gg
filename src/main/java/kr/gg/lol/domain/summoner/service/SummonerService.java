package kr.gg.lol.domain.summoner.service;

import kr.gg.lol.common.util.Rest;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kr.gg.lol.common.constant.CacheConstants.LEAGUE_ID;
import static kr.gg.lol.common.constant.CacheConstants.SUMMONER_NAME;
import static kr.gg.lol.common.util.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class SummonerService{

    private RestTemplate restTemplate = new RestTemplate();

    private final Rest rest;

    private final SummonerRepository summonerRepository;

    private final SummonerJdbcRepository summonerJdbcRepository;

    private final LeagueRepository leagueRepository;

    @Cacheable(value = SUMMONER_NAME, key = "#name")
    public ResponseEntity getSummonerByName(String name){

        Optional<Summoner> summoner = summonerRepository.findByName(name);

        if(summoner.isEmpty()){

            URI uri = UriComponentsBuilder
                    .fromUriString("https://kr.api.riotgames.com")
                    .path("/lol/summoner/v4/summoners/by-name/{name}")
                    .encode()
                    .build()
                    .expand(name)
                    .toUri();

            ResponseEntity<SummonerDto> response = rest.get(uri, SummonerDto.class);
            saveSummoner(response);
            return response;
        }

        return ResponseEntity.ok(toDto(summoner.get()));
    }

    @Cacheable(value = LEAGUE_ID, key = "#id")
    public ResponseEntity getLeagueById(String id){

        Optional<List<League>> leagues = leagueRepository.findBySummonerId(id);

        if(leagues.isEmpty()){

            URI uri = UriComponentsBuilder
                    .fromUriString("https://kr.api.riotgames.com")
                    .path("/lol/league/v4/entries/by-summoner/{id}")
                    .encode()
                    .build()
                    .expand(id)
                    .toUri();

            ResponseEntity<List<LeagueDto>> response = rest.get(uri, new ParameterizedTypeReference<List<LeagueDto>>() {});
            saveLeagues(response);
            return response;
        }

        return ResponseEntity.ok(toDto(leagues.get()));

    }

    private List<LeagueDto> toDto(List<League> leagues){
        List<LeagueDto> leagueDtos = new ArrayList<>();
        leagues.forEach(e-> {

            LeagueDto leagueDto = LeagueDto.builder()
                    .leaguePoints(e.getLeaguePoints())
                    .wins(e.getWins())
                    .losses(e.getLosses())
                    .summonerId(e.getSummonerId())
                    .summonerName(e.getSummonerName())
                    .tier(e.getTier())
                    .queueType(e.getQueueType())
                    .rank(e.getRank())
                    .build();

            leagueDtos.add(leagueDto);
        });

        return leagueDtos;
    }


    private SummonerDto toDto(Summoner summoner){
        return SummonerDto.builder()
                .id(summoner.getId())
                .accountId(summoner.getAccountId())
                .name(summoner.getName())
                .puuid(summoner.getPuuid())
                .summonerLevel(summoner.getSummonerLevel())
                .profileIconId(summoner.getProfileIconId())
                .build();
    }

    private void saveSummoner(ResponseEntity<SummonerDto> response){
        checkNotNull(response);

        SummonerDto dto = response.getBody();

        Summoner summoner = Summoner.builder()
                .accountId(dto.getAccountId())
                .puuid(dto.getPuuid())
                .name(dto.getName())
                .profileIconId(dto.getProfileIconId())
                .summonerLevel(dto.getSummonerLevel())
                .id(dto.getId())
                .build();

        summonerRepository.save(summoner);

    }

    private void saveLeagues(ResponseEntity<List<LeagueDto>> response){
        checkNotNull(response);

        List<LeagueDto> leagueDtos = response.getBody();
        List<League> leagues = new ArrayList<>();

        for(LeagueDto dto : leagueDtos){

            League league = League.builder()
                    .queueType(dto.getQueueType())
                    .summonerId(dto.getSummonerId())
                    .summonerName(dto.getSummonerName())
                    .leagueId(dto.getLeagueId())
                    .rank(dto.getRank())
                    .tier(dto.getTier())
                    .wins(dto.getWins())
                    .losses(dto.getLosses())
                    .build();

            leagues.add(league);
        }

        summonerJdbcRepository.bulkInsert(leagues);

    }











}
