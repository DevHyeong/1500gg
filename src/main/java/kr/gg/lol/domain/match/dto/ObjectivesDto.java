package kr.gg.lol.domain.match.dto;

import kr.gg.lol.domain.match.entity.Team;
import lombok.Getter;

@Getter
public class ObjectivesDto {
    private ObjectiveDto baron;
    private ObjectiveDto champion;
    private ObjectiveDto dragon;
    private ObjectiveDto inhibitor;
    private ObjectiveDto riftHerald;
    private ObjectiveDto tower;

    public ObjectivesDto(){

    }
    public ObjectivesDto(Team team){
        this.baron = new ObjectiveDto(team.isFirstBaron(), team.getKillsBaron());
        this.champion = new ObjectiveDto(team.isFirstChampion(), team.getKillsChampion());
        this.dragon = new ObjectiveDto(team.isFirstDragon(), team.getKillsDragon());
        this.inhibitor = new ObjectiveDto(team.isFirstInhibitor(), team.getKillsInhibitor());
        this.riftHerald = new ObjectiveDto(team.isFirstRiftHerald(), team.getKillsRiftHerald());
        this.tower = new ObjectiveDto(team.isFirstTower(), team.getKillsTower());
    }

}
