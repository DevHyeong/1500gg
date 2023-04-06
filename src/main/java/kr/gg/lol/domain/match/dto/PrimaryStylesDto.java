package kr.gg.lol.domain.match.dto;

import kr.gg.lol.domain.match.entity.PerkPrimary;

public class PrimaryStylesDto extends StylesDto{
    public PrimaryStylesDto(PerkPrimary perkPrimary){
        this.description = perkPrimary.description;
        this.selections.add(new SelectionsDto(perkPrimary.getPerk1(), perkPrimary.getVar11(), perkPrimary.getVar12(), perkPrimary.getVar13()));
        this.selections.add(new SelectionsDto(perkPrimary.getPerk2(), perkPrimary.getVar21(), perkPrimary.getVar22(), perkPrimary.getVar23()));
        this.selections.add(new SelectionsDto(perkPrimary.getPerk3(), perkPrimary.getVar31(), perkPrimary.getVar32(), perkPrimary.getVar33()));
        this.selections.add(new SelectionsDto(perkPrimary.getPerk4(), perkPrimary.getVar41(), perkPrimary.getVar42(), perkPrimary.getVar43()));
        this.style = perkPrimary.getStyle();
    }
}
