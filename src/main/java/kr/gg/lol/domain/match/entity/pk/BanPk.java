package kr.gg.lol.domain.match.entity.pk;


import kr.gg.lol.domain.match.entity.Team;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
public class BanPk implements Serializable {

    private String matchId;
    private int teamId;
    //private Team team;
    private int pickTurn;
}
