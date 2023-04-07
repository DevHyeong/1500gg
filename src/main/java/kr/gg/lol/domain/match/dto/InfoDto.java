package kr.gg.lol.domain.match.dto;

import kr.gg.lol.common.util.DateTimeUtils;
import kr.gg.lol.domain.match.entity.Match;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static kr.gg.lol.common.util.DateTimeUtils.unixTimestampOf;
import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
public class InfoDto {

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

    private List<ParticipantDto> participants;
    private List<TeamDto> teams;
    public InfoDto(){

    }
    public InfoDto(Match source){
        copyProperties(source, this);
        this.gameCreation = unixTimestampOf(source.getGameCreation());
        this.gameStartTimestamp = unixTimestampOf(source.getGameStartTime());
        this.gameEndTimestamp = unixTimestampOf(source.getGameEndTime());
        this.participants = source.getParticipants()
                .stream()
                .map(e-> new ParticipantDto(e))
                .collect(toList());

        this.teams = source.getTeams()
                .stream()
                .map(e-> new TeamDto(e, source.getParticipants()))
                .collect(toList());
    }
}
