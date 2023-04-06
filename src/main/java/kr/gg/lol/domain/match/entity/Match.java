package kr.gg.lol.domain.match.entity;

import kr.gg.lol.domain.match.dto.InfoDto;
import kr.gg.lol.domain.match.dto.MatchDto;
import kr.gg.lol.domain.match.dto.ParticipantDto;
import kr.gg.lol.domain.match.dto.TeamDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@Entity
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


    public Match(){

    }

    public Match(MatchDto source){
        copyProperties(source.getInfo(), this);
        this.id = source.getMetadata().getMatchId();
        this.participants = source.getInfo().getParticipants()
                .stream()
                .map(e-> new Participant(id, e))
                .collect(Collectors.toList());
        this.teams = source.getInfo().getTeams()
                .stream()
                .map(e-> new Team(id, e))
                .collect(Collectors.toList());
    }
}
