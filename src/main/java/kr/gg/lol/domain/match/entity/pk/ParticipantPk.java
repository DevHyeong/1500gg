package kr.gg.lol.domain.match.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantPk implements Serializable {

    private String matchId;
    private int participantId;
}
