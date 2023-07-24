package kr.gg.lol.domain.summoner.dto;

import kr.gg.lol.domain.summoner.entity.Summoner;
import lombok.*;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummonerDto {
    private String accountId;
    private int profileIconId;
    private Long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;

    public SummonerDto(Summoner source){
        copyProperties(source, this);
    }
}
