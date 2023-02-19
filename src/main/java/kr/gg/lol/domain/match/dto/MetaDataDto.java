package kr.gg.lol.domain.match.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaDataDto {

    private String dataVersion;
    private String matchId;
    private List<String> participants;

}
