package kr.gg.lol.domain.match.dto;

import kr.gg.lol.domain.match.entity.Participant;
import kr.gg.lol.domain.match.entity.PerkPrimary;
import kr.gg.lol.domain.match.entity.PerkSub;
import kr.gg.lol.domain.match.entity.Perks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantDto {

    private PerksDto perks;

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
    private int participantId;
    private int pentaKills;

    private int physicalDamageDealt;
    private int physicalDamageDealtToChampions;
    private int physicalDamageTaken;
    private int profileIcon;
    private String puuid;
    private int quadraKills;
    private String riotIdName;
    private String riotIdTagline;
    private String role;
    private int sightWardsBoughtInGame;
    private int spell1Casts;
    private int spell2Casts;
    private int spell3Casts;
    private int spell4Casts;
    private int summoner1Casts;
    private int summoner1Id;
    private int summoner2Casts;
    private int summoner2Id;
    private String summonerId;
    private int summonerLevel;
    private String summonerName;
    private boolean teamEarlySurrendered;
    private int teamId;
    private String teamPosition;
    private int timeCCingOthers;
    private int timePlayed;
    private int totalDamageDealt;
    private int totalDamageDealtToChampions;
    private int totalDamageShieldedOnTeammates;
    private int totalDamageTaken;
    private int totalHeal;
    private int totalHealsOnTeammates;
    private int totalMinionsKilled;
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

    public static ParticipantDto toDto(Participant p){

        PerkPrimary pp = p.getPerkPrimary();
        PerkSub ps = p.getPerkSub();

        PerksDto perksDto = new PerksDto();

        List<StylesDto> styles = new ArrayList<>();

        StylesDto primary = new StylesDto();
        primary.setDescription(pp.description);
        primary.getSelections().add(new SelectionsDto(pp.getPerk1(), pp.getVar11(), pp.getVar12(), pp.getVar13()));
        primary.getSelections().add(new SelectionsDto(pp.getPerk2(), pp.getVar21(), pp.getVar22(), pp.getVar23()));
        primary.getSelections().add(new SelectionsDto(pp.getPerk3(), pp.getVar31(), pp.getVar32(), pp.getVar33()));
        primary.getSelections().add(new SelectionsDto(pp.getPerk4(), pp.getVar41(), pp.getVar42(), pp.getVar43()));
        primary.setStyle(pp.getStyle());

        StylesDto sub = new StylesDto();
        sub.setDescription(ps.description);
        sub.getSelections().add(new SelectionsDto(ps.getPerk1(), ps.getVar11(), ps.getVar12(), ps.getVar13()));
        sub.getSelections().add(new SelectionsDto(ps.getPerk2(), ps.getVar21(), ps.getVar22(), ps.getVar23()));
        sub.setStyle(ps.getStyle());

        styles.add(primary);
        styles.add(sub);

        perksDto.setStyles(styles);

        return builder()
                .perks(perksDto)
                .participantId(p.getParticipantId())
                .assists(p.getAssists())
                .baronKills(p.getBaronKills())
                .bountyLevel(p.getBountyLevel())
                .champExperience(p.getChampExperience())
                .championId(p.getChampionId())
                .championName(p.getChampionName())
                .championTransform(p.getChampionTransform())
                .champLevel(p.getChampLevel())
                .consumablesPurchased(p.getConsumablesPurchased())
                .damageDealtToBuildings(p.getDamageDealtToBuildings())
                .damageDealtToObjectives(p.getDamageDealtToObjectives())
                .damageDealtToTurrets(p.getDamageDealtToTurrets())
                .damageSelfMitigated(p.getDamageSelfMitigated())
                .deaths(p.getDeaths())
                .detectorWardsPlaced(p.getDetectorWardsPlaced())
                .doubleKills(p.getDoubleKills())
                .dragonKills(p.getDragonKills())
                .firstBloodAssist(p.isFirstBloodAssist())
                .firstBloodKill(p.isFirstBloodKill())
                .firstTowerAssist(p.isFirstTowerAssist())
                .firstTowerKill(p.isFirstTowerKill())
                .gameEndedInEarlySurrender(p.isGameEndedInEarlySurrender())
                .gameEndedInSurrender(p.isGameEndedInSurrender())
                .goldEarned(p.getGoldEarned())
                .goldSpent(p.getGoldSpent())
                .individualPosition(p.getIndividualPosition())
                .inhibitorKills(p.getInhibitorKills())
                .inhibitorsLost(p.getInhibitorsLost())
                .inhibitorTakedowns(p.getInhibitorTakedowns())
                .item0(p.getItem0())
                .item1(p.getItem1())
                .item2(p.getItem2())
                .item3(p.getItem3())
                .item4(p.getItem4())
                .item5(p.getItem5())
                .item6(p.getItem6())
                .itemsPurchased(p.getItemsPurchased())
                .killingSprees(p.getKillingSprees())
                .kills(p.getKills())
                .lane(p.getLane())
                .largestCriticalStrike(p.getLargestCriticalStrike())
                .largestKillingSpree(p.getLargestKillingSpree())
                .largestMultiKill(p.getLargestMultiKill())
                .longestTimeSpentLiving(p.getLongestTimeSpentLiving())
                .magicDamageDealt(p.getMagicDamageDealt())
                .magicDamageDealtToChampions(p.getMagicDamageDealtToChampions())
                .magicDamageTaken(p.getMagicDamageTaken())
                .neutralMinionsKilled(p.getNeutralMinionsKilled())
                .nexusKills(p.getNexusKills())
                .nexusLost(p.getNexusLost())
                .objectivesStolen(p.getObjectivesStolen())
                .objectivesStolenAssists(p.getObjectivesStolenAssists())
                .pentaKills(p.getPentaKills())
                .physicalDamageDealt(p.getPhysicalDamageDealt())
                .physicalDamageDealtToChampions(p.getPhysicalDamageDealtToChampions())
                .physicalDamageTaken(p.getPhysicalDamageTaken())
                .puuid(p.getPuuid())
                .quadraKills(p.getQuadraKills())
                .role(p.getRole())
                .sightWardsBoughtInGame(p.getSightWardsBoughtInGame())
                .spell1Casts(p.getSpell1Casts())
                .spell2Casts(p.getSpell2Casts())
                .spell3Casts(p.getSpell3Casts())
                .spell4Casts(p.getSpell4Casts())
                .summoner1Casts(p.getSummoner1Casts())
                .summoner1Id(p.getSummoner1Id())
                .summoner2Casts(p.getSummoner2Casts())
                .summoner2Id(p.getSummoner2Id())
                .summonerId(p.getSummonerId())
                .summonerLevel(p.getSummonerLevel())
                .summonerName(p.getSummonerName())
                .teamEarlySurrendered(p.isTeamEarlySurrendered())
                .teamId(p.getTeamId())
                .teamPosition(p.getTeamPosition())
                .timeCCingOthers(p.getTimeCCingOthers())
                .timePlayed(p.getTimePlayed())
                .totalDamageDealt(p.getTotalDamageDealt())
                .totalDamageDealtToChampions(p.getTotalDamageDealtToChampions())
                .totalDamageShieldedOnTeammates(p.getTotalDamageShieldedOnTeammates())
                .totalDamageTaken(p.getTotalDamageTaken())
                .totalHeal(p.getTotalHeal())
                .totalHealsOnTeammates(p.getTotalHealsOnTeammates())
                .totalMinionsKilled(p.getTotalMinionsKilled())
                .totalTimeCCDealt(p.getTotalTimeCCDealt())
                .totalTimeSpentDead(p.getTotalTimeSpentDead())
                .totalUnitsHealed(p.getTotalUnitsHealed())
                .tripleKills(p.getTripleKills())
                .trueDamageDealt(p.getTrueDamageDealt())
                .trueDamageDealtToChampions(p.getTrueDamageDealtToChampions())
                .trueDamageTaken(p.getTrueDamageTaken())
                .turretKills(p.getTurretKills())
                .turretTakedowns(p.getTurretTakedowns())
                .turretsLost(p.getTurretsLost())
                .unrealKills(p.getUnrealKills())
                .visionScore(p.getVisionScore())
                .visionWardsBoughtInGame(p.getVisionWardsBoughtInGame())
                .wardsKilled(p.getWardsKilled())
                .wardsPlaced(p.getWardsPlaced())
                .win(p.isWin())
                .build();


    }


}
