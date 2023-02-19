package kr.gg.lol.domain.match.service;

import kr.gg.lol.domain.match.dto.*;
import kr.gg.lol.domain.match.entity.Ban;
import kr.gg.lol.domain.match.entity.Match;
import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.Team;
import kr.gg.lol.domain.match.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private RestTemplate restTemplate = new RestTemplate();
    private final MatchJdbcRepository matchJdbcRepository;
    private final MatchRepository matchRepository;

    private @Value("${lol.api.key}") String key;

    /**
     *   매치 리스트
     *
     * */
    public ResponseEntity getMatchesByPuuid(String puuid){

        Optional<List<String>> matches = matchJdbcRepository.findMatchesByPuuid(puuid);

        if(matches.isEmpty()){

            URI uri = UriComponentsBuilder
                    .fromUriString("https://asia.api.riotgames.com")
                    .path("/lol/match/v5/matches/by-puuid/{puuid}/ids")
                    .encode()
                    .build()
                    .expand(puuid)
                    .toUri();

            RequestEntity requestEntity = RequestEntity.get(uri)
                    .header("X-Riot-Token", key)
                    .build();


            ResponseEntity<List<String>> response = restTemplate.exchange(uri, HttpMethod.GET,
                    requestEntity, new ParameterizedTypeReference<List<String>>() {});

            matchJdbcRepository.bulkInsertMatches(puuid, response.getBody());

            return response;
        }

        return ResponseEntity.ok(matches.get());


    }

    /**
     *   매치 상세정보
     *
     * */

    public ResponseEntity getMatchByMatchId(String matchId){

        Optional<Match> match = matchRepository.findById(matchId);

        if(match.isEmpty()){

            URI uri = UriComponentsBuilder
                    .fromUriString("https://asia.api.riotgames.com")
                    .path("/lol/match/v5/matches/{matchId}")
                    .encode()
                    .build()
                    .expand(matchId)
                    .toUri();

            RequestEntity requestEntity = RequestEntity.get(uri)
                    .header("X-Riot-Token", key)
                    .build();

            ResponseEntity<MatchDto> response = restTemplate.exchange(uri, HttpMethod.GET,
                    requestEntity, MatchDto.class);

            saveMatch(response.getBody());

            return response;

        }

        return ResponseEntity.ok(toDto(match.get()));

    }

    /**
     *  현재 게임중 정보
     *
     * */
    public ResponseEntity getActiveMatchById(String id){

        URI uri = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com")
                .path("/lol/spectator/v4/active-games/by-summoner/{id}")
                .encode()
                .build()
                .expand(id)
                .toUri();

        RequestEntity requestEntity = RequestEntity.get(uri)
                .header("X-Riot-Token", key)
                .build();

        ResponseEntity<CurrentGameInfoDto> response = restTemplate.exchange(uri, HttpMethod.GET,
                requestEntity, CurrentGameInfoDto.class);

        return response;
    }




    private void saveMatch(MatchDto dto){
        Match match = Match.toEntity(dto);
        matchJdbcRepository.saveWithoutRelation(match);
        matchJdbcRepository.bulkInsertParticipants(match.getParticipants());
        matchJdbcRepository.bulkInsertTeams(match.getTeams());

        List<Ban> bans = new ArrayList<>();
        match.getTeams().stream().forEach(e-> {
            bans.addAll(e.getBans());
        });

        matchJdbcRepository.bulkInsertBans(bans);


        dto.getInfo().getTeams().forEach(t-> {

            int kills = dto.getInfo().getParticipants().stream().filter(p-> t.getTeamId() == p.getTeamId())
                    .mapToInt(ParticipantDto::getKills)
                    .sum();
            t.setKillsChampion(kills);
        });

    }

    private MatchDto toDto(Match match){

        MetaDataDto metaDataDto = MetaDataDto.builder()
                .matchId(match.getId())
                .build();

        List<ParticipantDto> participantDtos = new ArrayList<>();
        List<TeamDto> teamDtos = new ArrayList<>();

        for(Participant p : match.getParticipants())
            participantDtos.add(ParticipantDto.toDto(p));

        for(Team t : match.getTeams()){

            TeamDto teamDto = TeamDto.toDto(t);
            List<BanDto> banDtos = new ArrayList<>();

            int kills = match.getParticipants().stream().filter(e-> e.getTeamId() == t.getTeamId())
                    .mapToInt(Participant::getKills)
                    .sum();


            for(Ban b : t.getBans())
                banDtos.add(BanDto.toDto(b));


            ObjectiveDto baron = new ObjectiveDto(t.isFirstBaron(), t.getKillsBaron());
            ObjectiveDto champion = new ObjectiveDto(t.isFirstChampion(), t.getKillsChampion());
            ObjectiveDto dragon = new ObjectiveDto(t.isFirstDragon(), t.getKillsDragon());
            ObjectiveDto inhibitor = new ObjectiveDto(t.isFirstInhibitor(), t.getKillsInhibitor());
            ObjectiveDto riftHerald = new ObjectiveDto(t.isFirstRiftHerald(), t.getKillsRiftHerald());
            ObjectiveDto tower = new ObjectiveDto(t.isFirstTower(), t.getKillsTower());

            ObjectivesDto objectivesDto = new ObjectivesDto(baron, champion, dragon, inhibitor, riftHerald, tower);

            teamDto.setBans(banDtos);
            teamDto.setObjectives(objectivesDto);
            teamDto.setKillsChampion(kills);

            teamDtos.add(teamDto);
        }




        InfoDto infoDto = InfoDto.builder()
                .gameCreation(match.getGameCreation())
                .gameDuration(match.getGameDuration())
                .gameEndTimestamp(match.getGameEndTimestamp())
                .gameId(match.getGameId())
                .gameMode(match.getGameMode())
                .gameName(match.getGameName())
                .gameStartTimestamp(match.getGameStartTimestamp())
                .gameType(match.getGameType())
                .gameVersion(match.getGameVersion())
                .mapId(match.getMapId())
                .platformId(match.getPlatformId())
                .queueId(match.getQueueId())
                .participants(participantDtos)
                .teams(teamDtos)
                .tournamentCode(match.getTournamentCode())
                .build();


        return MatchDto.builder()
                .info(infoDto)
                .metadata(metaDataDto)
                .build();

    }



}
