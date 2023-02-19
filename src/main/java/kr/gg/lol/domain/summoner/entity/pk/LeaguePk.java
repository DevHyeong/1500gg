package kr.gg.lol.domain.summoner.entity.pk;

import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
public class LeaguePk implements Serializable{

    @Id
    private String summonerId;

    @Id
    private String queueType;
}
