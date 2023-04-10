package kr.gg.lol.domain.match.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.gg.lol.common.util.DateTimeUtils;
import kr.gg.lol.domain.match.dto.TeamDto;
import kr.gg.lol.domain.match.entity.*;
import kr.gg.lol.domain.match.entity.pk.ParticipantPk;
import kr.gg.lol.domain.summoner.entity.League;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MatchJdbcRepository {

    static final String TABLE_MATCHES = "matches";
    static final String TABLE_MATCH = "match_info";
    static final String TABLE_PARTICIPANT = "participant";
    static final String TABLE_TEAM = "team";
    static final String TABLE_BAN = "ban";
    static final String TABLE_PERK_PRIMARY = "perk_primary";
    static final String TABLE_PERK_SUB = "perk_sub";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Optional<List<String>> findMatchesByPuuid(String puuid){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("puuid", puuid);

        String query = String.format("SELECT id FROM %s WHERE puuid = :puuid", TABLE_MATCHES);

        RowMapper<String> rowMapper = (ResultSet resultSet, int rowNum)
                -> String.valueOf(resultSet.getString("id"));

        List<String> result = namedParameterJdbcTemplate.query(query, params, rowMapper);

        return Optional.ofNullable(result.size() < 1 ? null : result);
    }

    public void saveWithoutRelation(Match match){

        String sql = String.format("INSERT INTO %s" +
                " (id, game_creation, game_duration, game_end_time, game_id, game_mode, game_name, game_start_time," +
                "game_type, game_version, map_id, platform_id, queue_id, tournament_code) " +
                "VALUES (:id, :gameCreation, :gameDuration, :gameEndTime, :gameId, :gameMode," +
                ":gameName, :gameStartTime, :gameType, :gameVersion, :mapId, :platformId, " +
                ":queueId, :tournamentCode) ON DUPLICATE KEY " +
                "UPDATE game_creation = :gameCreation, game_duration = :gameDuration, game_end_time = :gameEndTime, game_id = :gameId, " +
                "game_mode = :gameMode, game_name = :gameName, game_start_time = :gameStartTime, game_type = :gameType, " +
                "game_version = :gameVersion, map_id = :mapId, platform_id = :platformId, queue_id = :queueId, tournament_code = :tournamentCode", TABLE_MATCH);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Map<String, Object> map = objectMapper.convertValue(match, Map.class);
        map.put("gameCreation", match.getGameCreation());
        map.put("gameStartTime", match.getGameStartTime());
        map.put("gameEndTime", match.getGameEndTime());
        namedParameterJdbcTemplate.update(sql, map);
    }

    public void bulkInsertMatches(String puuid, List<String> matchIds){

        String sql = String.format("INSERT INTO %s (id, puuid) VALUES (:id, :puuid)", TABLE_MATCHES);

        SqlParameterSource[] params = matchIds
                .stream()
                .map(e-> {
                    MapSqlParameterSource paramMap = new MapSqlParameterSource();
                    paramMap.addValue("id", e);
                    paramMap.addValue("puuid", puuid);
                    return paramMap;
                })
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }



    public void bulkInsertMatches(List<Match> matches){

        String sql = String.format("INSERT INTO %s" +
                        " (id, game_creation, game_duration, game_end_timestamp, game_id, game_mode, game_name, game_start_timestamp," +
                        "game_type, game_version, map_id, platform_id, queue_id, tournament_code) " +
                        "VALUES (:id, :gameCreation, :gameDuration, :gameEndTimestamp, :gameId, :gameMode," +
                        ":gameName, :gameStartTimestamp, :gameType, :gameVersion, :mapId, :platformId, " +
                        ":queueId, :tournamentCode) ON DUPLICATE KEY " +
                        "UPDATE game_creation = :gameCreation, game_duration = :gameDuration, game_end_timestamp = :gameEndTimestamp, game_id = :gameId, " +
                        "game_mode = :gameMode, game_name = :gameName, game_start_timestamp = :gameStartTimestamp, game_type = :gameType, " +
                        "game_version = :gameVersion, map_id = :mapId, platform_id = :platformId, queue_id = :queueId, tournament_code = :tournamentCode"
                , TABLE_MATCH);

        SqlParameterSource[] params = matches
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    public void bulkInsertParticipants(List<Participant> participants){

        String sql = String.format("INSERT INTO %s" +
                " VALUES (:matchId, :participantId, :assists, :baronKills, :bountyLevel, :champExperience," +
                ":champLevel, :championId, :championName, :championTransform, :consumablesPurchased, :damageDealtToBuildings, " +
                ":damageDealtToObjectives, :damageDealtToTurrets, :damageSelfMitigated, :deaths, :detectorWardsPlaced, :doubleKills," +
                ":dragonKills, :firstBloodAssist, :firstBloodKill, :firstTowerAssist, :firstTowerKill, :gameEndedInEarlySurrender, :gameEndedInSurrender, " +
                ":goldEarned, :goldSpent, :individualPosition, :inhibitorKills, :inhibitorTakedowns, :inhibitorsLost, :item0, :item1, :item2, :item3, :item4, :item5, :item6, " +
                ":itemsPurchased, :killingSprees, :kills, :lane, :largestCriticalStrike, :largestKillingSpree, :largestMultiKill, :longestTimeSpentLiving, :magicDamageDealt, "+
                ":magicDamageDealtToChampions, :magicDamageTaken, :neutralMinionsKilled, :nexusKills, :nexusTakedowns, :nexusLost, :objectivesStolen, :objectivesStolenAssists, " +
                ":pentaKills, :physicalDamageDealt, :physicalDamageDealtToChampions, :physicalDamageTaken, :puuid, :quadraKills, :role, :sightWardsBoughtInGame, :spell1Casts, :spell2Casts, " +
                ":spell3Casts, :spell4Casts, :summoner1Casts, :summoner1Id, :summoner2Casts, :summoner2Id, :summonerId, :summonerLevel, :summonerName, :teamEarlySurrendered, :teamId, :teamPosition, " +
                ":timeCCingOthers, :timePlayed, :totalDamageDealt, :totalDamageDealtToChampions, :totalDamageShieldedOnTeammates, :totalDamageTaken, :totalHeal, :totalHealsOnTeammates, :totalMinionsKilled, " +
                ":totalTimeCCDealt, :totalTimeSpentDead, :totalUnitsHealed, :tripleKills, :trueDamageDealt, :trueDamageDealtToChampions, :trueDamageTaken, :turretKills, :turretTakedowns, :turretsLost, " +
                ":unrealKills, :visionScore, :visionWardsBoughtInGame, :wardsKilled, :wardsPlaced, :win) ON DUPLICATE KEY " +

                "UPDATE assists = :assists, baron_kills = :baronKills, bounty_level = :bountyLevel, champ_experience = :champExperience, " +
                "champ_level = :champLevel, champion_id = :championId, champion_name = :championName, champion_transform = :championTransform, " +
                "consumables_purchased = :consumablesPurchased, damage_dealt_to_buildings = :damageDealtToBuildings, damage_dealt_to_objectives = :damageDealtToObjectives, "+
                "damage_dealt_to_turrets = :damageDealtToTurrets, damage_self_mitigated = :damageSelfMitigated, deaths = :deaths, detector_wards_placed = :detectorWardsPlaced, " +
                "double_kills = :doubleKills, dragon_kills = :dragonKills, first_blood_assist = :firstBloodAssist, first_blood_kill = :firstBloodKill, first_tower_assist = :firstTowerAssist, " +
                "first_tower_kill = :firstTowerKill, game_Ended_In_Early_Surrender = :gameEndedInEarlySurrender, game_Ended_In_Surrender = :gameEndedInSurrender, gold_Earned = :goldEarned, " +
                "gold_Spent = :goldSpent, individual_Position = :individualPosition, inhibitor_Kills = :inhibitorKills, inhibitor_Takedowns = :inhibitorTakedowns, inhibitors_Lost = :inhibitorsLost, " +
                "item0 = :item0, item1 = :item1, item2 = :item2, item3 = :item3, item4 = :item4, item5 = :item5, item6 = :item6, items_Purchased = :itemsPurchased, killing_Sprees = :killingSprees, kills = :kills, " +
                "lane = :lane, largest_Critical_Strike = :largestCriticalStrike, largest_Killing_Spree = :largestKillingSpree, largest_Multi_Kill = :largestMultiKill, longest_Time_Spent_Living = :longestTimeSpentLiving, " +
                "magic_Damage_Dealt = :magicDamageDealt, magic_Damage_Dealt_To_Champions = :magicDamageDealtToChampions, magic_Damage_Taken = :magicDamageTaken, neutral_Minions_Killed = :neutralMinionsKilled, nexus_Kills = :nexusKills, " +
                "nexus_Takedowns = :nexusTakedowns, nexus_Lost = :nexusLost, objectives_Stolen = :objectivesStolen, objectives_Stolen_Assists = :objectivesStolenAssists, penta_Kills = :pentaKills, physical_Damage_Dealt = :physicalDamageDealt, " +
                "physical_Damage_Dealt_To_Champions = :physicalDamageDealtToChampions, physical_Damage_Taken = :physicalDamageTaken, puuid = :puuid, quadra_Kills = :quadraKills, role_ = :role, sight_Wards_Bought_In_Game = :sightWardsBoughtInGame, " +
                "spell1_Casts = :spell1Casts, spell2_Casts = :spell2Casts, spell3_Casts = :spell3Casts, spell4_Casts = :spell4Casts, summoner1_Casts = :summoner1Casts, summoner1_Id = :summoner1Id, summoner2_Casts = :summoner2Casts, summoner2_Id = :summoner2Id, " +
                "summoner_Id = :summonerId, summoner_Level = :summonerLevel, summoner_Name = :summonerName, team_Early_Surrendered = :teamEarlySurrendered, team_Id = :teamId, team_position = :teamPosition, time_CCing_Others = :timeCCingOthers, " +
                "time_Played = :timePlayed, total_Damage_Dealt = :totalDamageDealt, total_Damage_Dealt_To_Champions = :totalDamageDealtToChampions, total_Damage_Shielded_On_Teammates = :totalDamageShieldedOnTeammates, total_Damage_Taken = :totalDamageTaken, " +
                "total_Heal = :totalHeal, total_Heals_On_Teammates = :totalHealsOnTeammates, total_Minions_Killed = :totalMinionsKilled, total_Time_CC_Dealt = :totalTimeCCDealt, total_Time_Spent_Dead = :totalTimeSpentDead, total_Units_Healed = :totalUnitsHealed, " +
                "triple_Kills = :tripleKills, true_Damage_Dealt = :trueDamageDealt, true_Damage_Dealt_To_Champions = :trueDamageDealtToChampions, true_Damage_Taken = :trueDamageTaken, turret_Kills = :turretKills, turret_Takedowns = :turretTakedowns, " +
                "turrets_Lost = :turretsLost, unreal_Kills = :unrealKills, vision_Score = :visionScore, vision_Wards_Bought_In_Game = :visionWardsBoughtInGame, wards_Killed = :wardsKilled, wards_Placed = :wardsPlaced, win = :win", TABLE_PARTICIPANT);

        SqlParameterSource[] params = participants
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
        bulkInsertPerkPrimary(participants.stream().map(e-> e.getPerkPrimary()).collect(Collectors.toList()));
        bulkInsertPerkSub(participants.stream().map(e-> e.getPerkSub()).collect(Collectors.toList()));

    }

    public void bulkInsertTeams(List<Team> teams){

        String sql = String.format("INSERT INTO %s" +
                " (match_id, team_id, first_baron, kills_baron, first_champion, kills_champion, first_dragon, kills_dragon," +
                "first_inhibitor, kills_inhibitor, first_tower, kills_tower, first_rift_herald, kills_rift_herald, win) " +
                "VALUES (:matchId, :teamId, :firstBaron, :killsBaron, :firstChampion, :killsChampion," +
                ":firstDragon, :killsDragon, :firstInhibitor, :killsInhibitor, :firstTower, :killsTower, " +
                ":firstRiftHerald, :killsRiftHerald, :win) ON DUPLICATE KEY " +
                "UPDATE first_baron = :firstBaron, kills_baron = :killsBaron, first_champion = :firstChampion, kills_champion = :killsChampion, " +
                "first_dragon = :firstDragon, kills_dragon = :killsDragon, first_inhibitor = :firstInhibitor, kills_inhibitor = :killsInhibitor, " +
                "first_tower = :firstTower, kills_tower = :killsTower, first_rift_herald = :firstRiftHerald, "+
                " kills_rift_herald = :killsRiftHerald, win = :win", TABLE_TEAM);

        SqlParameterSource[] params = teams
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);

    }

    public void bulkInsertBans(List<Ban> bans){

        String sql = String.format("INSERT INTO %s" +
                " (match_id, team_id, pick_turn, champion_id) " +
                "VALUES (:matchId, :teamId, :pickTurn, :championId) ON DUPLICATE KEY " +
                "UPDATE pick_turn = :pickTurn, champion_id = :championId", TABLE_BAN);

        SqlParameterSource[] params = bans
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);

    }

    public void bulkInsertPerkPrimary(List<PerkPrimary> perkPrimaries){

        String sql = String.format("INSERT INTO %s" +
                " (match_id, participant_id, style, perk1, var11, var12, var13, " +
                " perk2, var21, var22, var23, perk3, var31, var32, var33, perk4, var41, var42, var43) " +
                "VALUES (:matchId, :participantId, :style, :perk1, :var11, :var12, :var13, " +
                " :perk2, :var21, :var22, :var23, :perk3, :var31, :var32, :var33, :perk4, :var41, :var42, :var43) " +

                "ON DUPLICATE KEY UPDATE style = :style, perk1 = :perk1, var11 = :var11, var12 = :var12, var13 = :var13," +
                "perk2 = :perk2, var21 = :var21, var22 = :var22, var23 = :var23," +
                "perk3 = :perk3, var31 = :var31, var32 = :var32, var33 = :var33," +
                "perk4 = :perk4, var41 = :var41, var42 = :var42, var43 = :var43"
                , TABLE_PERK_PRIMARY);

        SqlParameterSource[] params = perkPrimaries
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);


    }

    public void bulkInsertPerkSub(List<PerkSub> perkSubs){

        String sql = String.format("INSERT INTO %s" +
                " (match_id, participant_id, style, perk1, var11, var12, var13, perk2, var21, var22, var23) " +
                "VALUES (:matchId, :participantId, :style, :perk1, :var11, :var12, :var13, " +
                " :perk2, :var21, :var22, :var23) " +

                "ON DUPLICATE KEY UPDATE style = :style, perk1 = :perk1, var11 = :var11, var12 = :var12, var13 = :var13," +
                "perk2 = :perk2, var21 = :var21, var22 = :var22, var23 = :var23"
                , TABLE_PERK_SUB);

        SqlParameterSource[] params = perkSubs
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);

    }
}
