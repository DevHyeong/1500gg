package kr.gg.lol.domain.match.entity;

import kr.gg.lol.domain.match.dto.BanDto;
import kr.gg.lol.domain.match.entity.pk.BanPk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ban")
@IdClass(BanPk.class)
public class Ban {

    @Id
    @Column(name = "match_id")
    private String matchId;

    @Id
    @Column(name = "team_id")
    private int teamId;

    @Id
    private int pickTurn;

    private int championId;

    public static Ban toEntity(String matchId, int teamId, BanDto banDto){

        return builder()
                .matchId(matchId)
                .teamId(teamId)
                .pickTurn(banDto.getPickTurn())
                .championId(banDto.getChampionId())

                .build();
    }


}
