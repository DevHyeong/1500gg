package kr.gg.lol.domain.summoner.entity;

import kr.gg.lol.domain.summoner.dto.SummonerDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Table(name = "summoner")
@Entity
@Getter
@Setter
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

    public Summoner(){

    }

    public Summoner(SummonerDto source){
        copyProperties(source, this);
    }


}
