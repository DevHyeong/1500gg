package kr.gg.lol.domain.summoner.service;

import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.entity.League;
import kr.gg.lol.domain.summoner.entity.Summoner;
import kr.gg.lol.domain.summoner.repository.LeagueRepository;
import kr.gg.lol.domain.summoner.repository.SummonerJdbcRepository;
import kr.gg.lol.domain.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private RestTemplate restTemplate = new RestTemplate();

    private @Value("${lol.api.key}") String key;

    private final SummonerRepository summonerRepository;

    private final SummonerJdbcRepository summonerJdbcRepository;

    private final LeagueRepository leagueRepository;

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

            RequestEntity requestEntity = RequestEntity.get(uri)
                    .header("X-Riot-Token", key)
                    .build();
            try{
                ResponseEntity<SummonerDto> response = restTemplate.exchange(uri, HttpMethod.GET,
                        requestEntity, SummonerDto.class);
                saveSummoner(response);
                return response;
            }catch (HttpClientErrorException e){
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(toDto(summoner.get()));
    }

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

            RequestEntity requestEntity = RequestEntity.get(uri)
                    .header("X-Riot-Token", key)
                    .build();

            ResponseEntity<List<LeagueDto>> response = restTemplate.exchange(uri, HttpMethod.GET,
                    requestEntity, new ParameterizedTypeReference<List<LeagueDto>>() {});

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
        Assert.notNull(response, "");

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