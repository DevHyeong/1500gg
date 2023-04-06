package kr.gg.lol.domain.match.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gg.lol.domain.match.dto.SelectionsDto;
import kr.gg.lol.domain.match.dto.StylesDto;
import kr.gg.lol.domain.match.entity.pk.ParticipantPk;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "perk_primary")
@IdClass(ParticipantPk.class)
public class PerkPrimary {
    @Transient
    public static final String description = "primaryStyle";

    @Id
    @Column(name = "match_id")
    private String matchId;

    @Id
    private int participantId;

    private int style;

    private int perk1;
    private int var11;
    private int var12;
    private int var13;

    private int perk2;
    private int var21;
    private int var22;
    private int var23;

    private int perk3;
    private int var31;
    private int var32;
    private int var33;

    private int perk4;
    private int var41;
    private int var42;
    private int var43;

    public PerkPrimary(){

    }
    public PerkPrimary(ParticipantPk participantPk, StylesDto stylesDto){
        int i = 1;
        for(SelectionsDto selectionsDto : stylesDto.getSelections()){
            try {
                getClass().getDeclaredField("perk" + i)
                        .set(this, selectionsDto.getPerk());
                getClass().getDeclaredField("var" + i + "1")
                        .set(this, selectionsDto.getVar1());
                getClass().getDeclaredField("var" + i + "2")
                        .set(this, selectionsDto.getVar2());
                getClass().getDeclaredField("var" + i + "3")
                        .set(this, selectionsDto.getVar3());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
        this.style = stylesDto.getStyle();
        this.matchId = participantPk.getMatchId();
        this.participantId = participantPk.getParticipantId();
    }


}
