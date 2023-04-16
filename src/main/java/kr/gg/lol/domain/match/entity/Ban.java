package kr.gg.lol.domain.match.entity;

import kr.gg.lol.domain.match.dto.BanDto;
import kr.gg.lol.domain.match.entity.pk.BanPk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
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
//    @Id
//    @ManyToOne
//    @JoinColumns({
//        @JoinColumn(name = "match_id", insertable = false, updatable = false),
//        @JoinColumn(name = "team_id", insertable = false, updatable = false)
//    })
//    private Team team;

    @Id
    private int pickTurn;

    private int championId;

    public Ban(){

    }

    public Ban(String matchId, int teamId, BanDto source){
        //this.matchId = matchId;
        //this.teamId = teamId;
        this.championId = source.getChampionId();
        this.pickTurn = source.getPickTurn();
    }
}
