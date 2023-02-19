package kr.gg.lol.domain.match.entity.pk;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
public class TeamPk implements Serializable {

    @Id
    @Column(name = "match_id")
    private String matchId;

    @Id
    private int teamId;

}
