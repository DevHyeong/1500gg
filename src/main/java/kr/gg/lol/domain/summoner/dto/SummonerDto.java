package kr.gg.lol.domain.summoner.dto;

import kr.gg.lol.domain.summoner.entity.Summoner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    public static SummonerDto toDto(Summoner summoner){
        return SummonerDto.builder()
                .id(summoner.getId())
                .accountId(summoner.getAccountId())
                .name(summoner.getName())
                .puuid(summoner.getPuuid())
                .summonerLevel(summoner.getSummonerLevel())
                .profileIconId(summoner.getProfileIconId())
                .build();
    }

}
