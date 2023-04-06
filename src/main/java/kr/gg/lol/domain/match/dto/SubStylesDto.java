package kr.gg.lol.domain.match.dto;

import kr.gg.lol.domain.match.entity.PerkSub;

public class SubStylesDto extends StylesDto {
    public SubStylesDto(PerkSub perkSub) {
        this.description = perkSub.description;
        this.selections.add(new SelectionsDto(perkSub.getPerk1(),perkSub.getVar11(),perkSub.getVar12(),perkSub.getVar13()));
        this.selections.add(new SelectionsDto(perkSub.getPerk2(),perkSub.getVar21(),perkSub.getVar22(),perkSub.getVar23()));
        this.style = perkSub.getStyle();
    }
}
