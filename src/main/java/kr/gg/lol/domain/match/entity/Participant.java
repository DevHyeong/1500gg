package kr.gg.lol.domain.match.entity;

import kr.gg.lol.domain.match.dto.ParticipantDto;
import kr.gg.lol.domain.match.dto.StylesDto;
import kr.gg.lol.domain.match.entity.pk.ParticipantPk;
import lombok.*;

import javax.persistence.*;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@Entity
@Table(name = "participant")
@IdClass(ParticipantPk.class)
public class Participant {

    @Id
    @Column(name = "match_id")
    private String matchId;

    @Id
    private int participantId;

    private int assists;
    private int baronKills;
    private int bountyLevel;
    private int champExperience;
    private int champLevel;
    private int championId;
    private String championName;
    private int championTransform;
    private int consumablesPurchased;
    private int damageDealtToBuildings;
    private int damageDealtToObjectives;
    private int damageDealtToTurrets;

    private int damageSelfMitigated;
    private int deaths;
    private int detectorWardsPlaced;
    private int doubleKills;
    private int dragonKills;
    private boolean firstBloodAssist;
    private boolean firstBloodKill;
    private boolean firstTowerAssist;
    private boolean firstTowerKill;
    private boolean gameEndedInEarlySurrender;
    private boolean gameEndedInSurrender;
    private int goldEarned;
    private int goldSpent;
    private String individualPosition;
    private int inhibitorKills;
    private int inhibitorTakedowns;
    private int inhibitorsLost;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private int itemsPurchased;
    private int killingSprees;
    private int kills;
    private String lane;
    private int largestCriticalStrike;
    private int largestKillingSpree;
    private int largestMultiKill;
    private int longestTimeSpentLiving;
    private int magicDamageDealt;
    private int magicDamageDealtToChampions;
    private int magicDamageTaken;
    private int neutralMinionsKilled;
    private int nexusKills;
    private int nexusTakedowns;
    private int nexusLost;
    private int objectivesStolen;
    private int objectivesStolenAssists;
    private int pentaKills;
    private int physicalDamageDealt;
    private int physicalDamageDealtToChampions;
    private int physicalDamageTaken;
    private String puuid;
    private int quadraKills;

    @Column(name="role_")
    private String role;

    private int sightWardsBoughtInGame;

    @Column(name="spell1_casts")
    private int spell1Casts;

    @Column(name="spell2_casts")
    private int spell2Casts;

    @Column(name="spell3_casts")
    private int spell3Casts;

    @Column(name="spell4_casts")
    private int spell4Casts;

    @Column(name="summoner1_casts")
    private int summoner1Casts;

    @Column(name="summoner1_id")
    private int summoner1Id;

    @Column(name="summoner2_casts")
    private int summoner2Casts;

    @Column(name="summoner2_id")
    private int summoner2Id;
    private String summonerId;
    private int summonerLevel;
    private String summonerName;
    private boolean teamEarlySurrendered;
    private int teamId;
    private String teamPosition;

    @Column(name = "time_ccing_others")
    private int timeCCingOthers;

    private int timePlayed;
    private int totalDamageDealt;
    private int totalDamageDealtToChampions;
    private int totalDamageShieldedOnTeammates;
    private int totalDamageTaken;
    private int totalHeal;
    private int totalHealsOnTeammates;
    private int totalMinionsKilled;

    @Column(name = "total_time_cc_dealt")
    private int totalTimeCCDealt;

    private int totalTimeSpentDead;
    private int totalUnitsHealed;
    private int tripleKills;
    private int trueDamageDealt;
    private int trueDamageDealtToChampions;
    private int trueDamageTaken;
    private int turretKills;
    private int turretTakedowns;
    private int turretsLost;
    private int unrealKills;
    private int visionScore;
    private int visionWardsBoughtInGame;
    private int wardsKilled;
    private int wardsPlaced;
    private boolean win;

    @OneToOne
    @JoinColumns(value = {
            @JoinColumn(name = "match_id", insertable = false, updatable = false),
            @JoinColumn(name = "participant_id", insertable = false, updatable = false)
    })
    private PerkPrimary perkPrimary;

    @OneToOne
    @JoinColumns(value = {
            @JoinColumn(name = "match_id", insertable = false, updatable = false),
            @JoinColumn(name = "participant_id", insertable = false, updatable = false)
    })
    private PerkSub perkSub;

    public Participant(){

    }

    public Participant(String matchId, ParticipantDto source){
        copyProperties(source, this);
        ParticipantPk participantPk = new ParticipantPk(matchId, source.getParticipantId());
        this.matchId = matchId;
        this.perkPrimary = source.getPerks().getStyles()
                .stream()
                .filter(e-> e.getDescription().equals("primaryStyle"))
                .findFirst()
                .map(e-> new PerkPrimary(participantPk, e))
                .orElseThrow(() -> new RuntimeException(""));
        this.perkSub = source.getPerks().getStyles()
                .stream()
                .filter(e-> e.getDescription().equals("subStyle"))
                .findFirst()
                .map(e-> new PerkSub(participantPk, e))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
