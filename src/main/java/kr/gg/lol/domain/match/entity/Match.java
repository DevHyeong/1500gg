package kr.gg.lol.domain.match.entity;

import kr.gg.lol.domain.match.dto.InfoDto;
import kr.gg.lol.domain.match.dto.MatchDto;
import kr.gg.lol.domain.match.dto.ParticipantDto;
import kr.gg.lol.domain.match.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "match_info")
public class Match {

    @Id
    private String id;

    private long gameCreation;
    private long gameDuration;
    private long gameEndTimestamp;
    private long gameId;
    private String gameMode;
    private String gameName;
    private long gameStartTimestamp;
    private String gameType;
    private String gameVersion;
    private int mapId;
    private String platformId;
    private int queueId;
    private String tournamentCode;

    @OneToMany//(fetch = FetchType.EAGER)
    @JoinColumn(name = "match_id", insertable = false, updatable = false)
    private List<Participant> participants;

    @OneToMany
    @JoinColumn(name = "match_id", insertable = false, updatable = false)
    private List<Team> teams;

    public static Match toEntity(MatchDto matchDto){

        String matchId = matchDto.getMetadata().getMatchId();
        InfoDto infoDto = matchDto.getInfo();
        List<ParticipantDto> participantDtos = infoDto.getParticipants();
        List<TeamDto> teamDtos = infoDto.getTeams();

        List<Participant> participants = new ArrayList<>();
        participantDtos.forEach(p-> {

            Participant participant = Participant.toEntity(matchId, p);
            participants.add(participant);

        });

        List<Team> teams = new ArrayList<>();
        teamDtos.forEach(t -> {
            Team team = Team.toEntity(matchId, t);
            teams.add(team);
        });


        return builder()
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
                .participants(participants)
                .teams(teams)
                .build();

    }

}
