package kr.gg.lol.domain.summoner.entity;


import kr.gg.lol.domain.summoner.entity.pk.LeaguePk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "league")
@IdClass(LeaguePk.class)
public class League {

    private String leagueId;

    @Id
    private String summonerId;
    private String summonerName;

    @Id
    private String queueType;
    private String tier;

    @Column(name = "rank1")
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private LocalDateTime updatedAt;


    @PrePersist
    @PreUpdate
    public void prePersist(){
        this.updatedAt = LocalDateTime.now();
    }
}
