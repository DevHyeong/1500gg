package kr.gg.lol.domain.summoner.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "summoner")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summoner {

    @Id
    private String name;

    private String accountId;
    private String id;
    private int profileIconId;
    private String puuid;
    private long summonerLevel;
    private LocalDateTime updatedAt;


    @PrePersist
    @PreUpdate
    public void prePersist(){
        this.updatedAt = LocalDateTime.now();
    }

}
