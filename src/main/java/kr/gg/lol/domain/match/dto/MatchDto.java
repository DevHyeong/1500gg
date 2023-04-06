package kr.gg.lol.domain.match.dto;


import kr.gg.lol.domain.match.entity.Match;
import lombok.*;

@Getter
public class MatchDto {

    private MetaDataDto metadata;
    private InfoDto info;

    public MatchDto(){

    }

    public MatchDto(Match source){
        this.metadata = new MetaDataDto(source.getId());
        this.info = new InfoDto(source);
    }

}
