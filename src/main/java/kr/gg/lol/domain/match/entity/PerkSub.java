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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "perk_sub")
@IdClass(ParticipantPk.class)
public class PerkSub {

    @Transient
    public static final String description = "subStyle";

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


    public static PerkSub toEntity(ParticipantPk participantPk, StylesDto stylesDto){

        Map<String, Object> map = new HashMap<>();
        int i = 1;

        for(SelectionsDto selectionsDto : stylesDto.getSelections()){
            map.put("perk" + i , selectionsDto.getPerk());
            map.put("var" + i + "1" , selectionsDto.getVar1());
            map.put("var" + i + "2" , selectionsDto.getVar2());
            map.put("var" + i + "3" ,  selectionsDto.getVar3());

            i++;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        PerkSub perkSub = objectMapper.convertValue(map, PerkSub.class);

        perkSub.setStyle(stylesDto.getStyle());
        perkSub.setMatchId(participantPk.getMatchId());
        perkSub.setParticipantId(participantPk.getParticipantId());

        return perkSub;

    }
}
