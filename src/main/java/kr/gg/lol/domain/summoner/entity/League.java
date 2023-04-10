package kr.gg.lol.domain.summoner.entity;


import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.entity.pk.LeaguePk;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
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

    public League(){

    }

    public League(LeagueDto source){
        copyProperties(source, this);
    }
}
