package kr.gg.lol.domain.match.entity.pk;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
public class TeamPk implements Serializable {

    private String matchId;

    private int teamId;

}
