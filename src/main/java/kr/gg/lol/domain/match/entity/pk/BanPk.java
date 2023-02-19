package kr.gg.lol.domain.match.entity.pk;


import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
public class BanPk implements Serializable {

    @Id
    @Column(name = "match_id")
    private String matchId;

    @Id
    @Column(name = "team_id")
    private int teamId;

    @Id
    private int pickTurn;
}
