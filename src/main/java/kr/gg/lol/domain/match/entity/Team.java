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

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public static Team toEntity(String matchId, TeamDto dto){

        List<BanDto> banDtos = dto.getBans();
        List<Ban> bans = new ArrayList<>();

        banDtos.forEach(e-> {
            Ban ban = Ban.toEntity(matchId, dto.getTeamId(), e);
            bans.add(ban);
        });

        return builder()
                .matchId(matchId)
                .firstBaron(dto.getObjectives().getBaron().isFirst())
                .killsBaron(dto.getObjectives().getBaron().getKills())
                .firstChampion(dto.getObjectives().getChampion().isFirst())
                .killsChampion(dto.getObjectives().getChampion().getKills())
                .firstDragon(dto.getObjectives().getDragon().isFirst())
                .killsDragon(dto.getObjectives().getDragon().getKills())
                .firstInhibitor(dto.getObjectives().getInhibitor().isFirst())
                .killsInhibitor(dto.getObjectives().getInhibitor().getKills())
                .firstRiftHerald(dto.getObjectives().getRiftHerald().isFirst())
                .killsRiftHerald(dto.getObjectives().getRiftHerald().getKills())
                .teamId(dto.getTeamId())
                .win(dto.isWin())
                .bans(bans)
                .build();

    }


}
