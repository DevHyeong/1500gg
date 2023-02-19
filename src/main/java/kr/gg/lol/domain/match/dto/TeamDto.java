package kr.gg.lol.domain.match.dto;


import kr.gg.lol.domain.match.entity.Team;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {

    private List<BanDto> bans;
    private ObjectivesDto objectives;
    private int killsChampion;
    private int teamId;
    private boolean win;

    public static TeamDto toDto(Team t){
        return builder()
                .teamId(t.getTeamId())
                .win(t.isWin())
                .build();
    }
}
