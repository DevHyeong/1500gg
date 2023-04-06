package kr.gg.lol.domain.match.dto;

import kr.gg.lol.domain.match.entity.Ban;
import lombok.*;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
public class BanDto {

    private int championId;
    private int pickTurn;
    private int teamId;

    public BanDto(){

    }
    public BanDto(Ban source){
        copyProperties(source, this);
    }

}
