package kr.gg.lol.domain.match.dto;

import kr.gg.lol.domain.match.entity.Ban;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanDto {

    private int championId;
    private int pickTurn;
    private int teamId;

    public static BanDto toDto(Ban ban){
        return builder()
                .championId(ban.getChampionId())
                .pickTurn(ban.getPickTurn())
                .build();
    }

}
