package kr.gg.lol.web.usecase;

import kr.gg.lol.domain.match.repository.MatchJdbcRepository;
import kr.gg.lol.domain.match.service.MatchService;
import kr.gg.lol.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor
public class MatchUpdateService {

    private final SummonerService summonerService;
    private final MatchService matchService;

    private final MatchJdbcRepository matchJdbcRepository;

    /**
     *  전적갱신
     *
     * */
    public void test(String name){

        /*ResponseEntity<SummonerDto> summonerResponse = summonerService.getSummonerByName(name);
        SummonerDto summonerDto = summonerResponse.getBody();

        summonerService.getLeagueById(summonerDto.getId());


        log.info(summonerDto.getName(), summonerDto.getPuuid());

        ResponseEntity<List<String>> matchesResponse = matchService.getMatchesByPuuid(summonerDto.getPuuid());

        List<String> matches = matchesResponse.getBody();
        List<Match> matchEntites = new ArrayList<>();

        matches.forEach(e-> {

            ResponseEntity<MatchDto> matchRes = matchService.getMatchByMatchId(e);
            MatchDto matchDto = matchRes.getBody();
            InfoDto infoDto = matchDto.getInfo();
            List<ParticipantDto> participantDtos = infoDto.getParticipants();
            String matchId = matchDto.getMetadata().getMatchId();

            Match match = Match.builder()
                    .id(matchId)
                    .gameCreation(infoDto.getGameCreation())
                    .gameDuration(infoDto.getGameDuration())
                    .gameEndTimestamp(infoDto.getGameEndTimestamp())
                    .gameId(infoDto.getGameId())
                    .gameMode(infoDto.getGameMode())
                    .gameName(infoDto.getGameName())
                    .gameStartTimestamp(infoDto.getGameStartTimestamp())
                    .gameType(infoDto.getGameType())
                    .gameVersion(infoDto.getGameVersion())
                    .platformId(infoDto.getPlatformId())
                    .queueId(infoDto.getQueueId())
                    .tournamentCode(infoDto.getTournamentCode())
                    .build();

            matchEntites.add(match);

            List<Participant> participants = new ArrayList<>();
            participantDtos.forEach(p-> {

                Participant participant = Participant.toEntity(matchId, p);
                participants.add(participant);

            });

            matchJdbcRepository.bulkInsertParticipants(participants);

        });

        matchJdbcRepository.bulkInsertMatches(matchEntites);*/

    }



}
