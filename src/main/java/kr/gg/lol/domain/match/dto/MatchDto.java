package kr.gg.lol.domain.match.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDto {

    private MetaDataDto metadata;
    private InfoDto info;

}
