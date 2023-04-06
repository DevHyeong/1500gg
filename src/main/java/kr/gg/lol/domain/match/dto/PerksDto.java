package kr.gg.lol.domain.match.dto;

import kr.gg.lol.domain.match.entity.PerkPrimary;
import kr.gg.lol.domain.match.entity.PerkSub;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PerksDto {
    private List<StylesDto> styles = new ArrayList<>();
    public PerksDto(){

    }
    public PerksDto(PerkPrimary perkPrimary, PerkSub perkSub){
        StylesDto primary = new PrimaryStylesDto(perkPrimary);
        StylesDto sub = new SubStylesDto(perkSub);
        this.styles.add(primary);
        this.styles.add(sub);
    }
}
