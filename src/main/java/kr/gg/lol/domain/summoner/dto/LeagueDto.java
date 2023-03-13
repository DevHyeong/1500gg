package kr.gg.lol.domain.summoner.dto;

import kr.gg.lol.domain.summoner.entity.League;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueDto {

    private String leagueId;
    private String summonerId;
    private String summonerName;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean hotStreak;
    private boolean veteran;
    private boolean freshBlood;
    private boolean inactive;

    public static List<LeagueDto> toDto(List<League> leagues){
        List<LeagueDto> leagueDtos = new ArrayList<>();
        leagues.forEach(e-> {

            LeagueDto leagueDto = LeagueDto.builder()
                    .leaguePoints(e.getLeaguePoints())
                    .wins(e.getWins())
                    .losses(e.getLosses())
                    .summonerId(e.getSummonerId())
                    .summonerName(e.getSummonerName())
                    .tier(e.getTier())
                    .queueType(e.getQueueType())
                    .rank(e.getRank())
                    .build();

            leagueDtos.add(leagueDto);
        });

        return leagueDtos;
    }


}
