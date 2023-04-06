package kr.gg.lol.domain.match.entity;

import kr.gg.lol.domain.match.dto.BanDto;
import kr.gg.lol.domain.match.dto.ObjectiveDto;
import kr.gg.lol.domain.match.dto.ObjectivesDto;
import kr.gg.lol.domain.match.dto.TeamDto;
import kr.gg.lol.domain.match.entity.pk.TeamPk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "team")
@IdClass(TeamPk.class)
public class Team {

    @Id
    @Column(name = "match_id")
    private String matchId;

    @Id
    private int teamId;

    private boolean firstBaron;
    private int killsBaron;
    private boolean firstChampion;
    private int killsChampion;
    private boolean firstDragon;
    private int killsDragon;
    private boolean firstInhibitor;
    private int killsInhibitor;
    private boolean firstTower;
    private int killsTower;
    private boolean firstRiftHerald;
    private int killsRiftHerald;

    private boolean win;

    @OneToMany
    @JoinColumns(value = {
            @JoinColumn(name = "match_id", insertable = false, updatable = false),
            @JoinColumn(name = "team_id", insertable = false, updatable = false)
    })
    private List<Ban> bans;

    public Team(){

    }

    public Team(String matchId, TeamDto source){
        this.matchId = matchId;
        this.teamId = source.getTeamId();
        this.win = source.isWin();
        this.firstBaron = source.getObjectives().getBaron().isFirst();
        this.killsBaron = source.getObjectives().getBaron().getKills();
        this.firstChampion = source.getObjectives().getChampion().isFirst();
        this.killsChampion = source.getObjectives().getChampion().getKills();
        this.firstDragon = source.getObjectives().getDragon().isFirst();
        this.killsDragon = source.getObjectives().getDragon().getKills();
        this.firstInhibitor = source.getObjectives().getInhibitor().isFirst();
        this.killsInhibitor = source.getObjectives().getInhibitor().getKills();
        this.firstRiftHerald = source.getObjectives().getRiftHerald().isFirst();
        this.killsRiftHerald = source.getObjectives().getRiftHerald().getKills();
        this.bans = source.getBans()
                .stream()
                .map(e-> new Ban(matchId, source.getTeamId(), e))
                .collect(Collectors.toList());
    }
}
