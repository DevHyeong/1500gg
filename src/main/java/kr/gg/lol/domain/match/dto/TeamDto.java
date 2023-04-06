package kr.gg.lol.domain.match.dto;

import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.Team;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Setter
@Getter
public class TeamDto {
    private List<BanDto> bans = new ArrayList<>();
    private ObjectivesDto objectives;
    private int killsChampion;
    private int teamId;
    private boolean win;

    public TeamDto(){

    }
    public TeamDto(Team team, List<Participant> participants){
        this.teamId = team.getTeamId();
        this.win = team.isWin();
        this.killsChampion = participants.stream().filter(e-> e.getTeamId() == team.getTeamId())
                .mapToInt(Participant::getKills)
                .sum();
        this.bans = team.getBans().stream().map(e-> new BanDto(e)).collect(toList());
        this.objectives = new ObjectivesDto(team);
    }
}
