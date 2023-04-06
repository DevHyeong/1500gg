package kr.gg.lol.domain.match.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class MetaDataDto {

    private String dataVersion;
    private String matchId;
    private List<String> participants;

    public MetaDataDto(){

    }
    public MetaDataDto(String matchId){
        this.matchId = matchId;
    }
}
