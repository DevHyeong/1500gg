package kr.gg.lol.domain.summoner.repository;

import kr.gg.lol.domain.summoner.entity.League;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SummonerJdbcRepository {

    static final String TABLE_LEAGUE = "league";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void bulkInsert(List<League> leagues){

       String sql = String.format("INSERT INTO %s" +
                " (queue_type, league_id, summoner_id, summoner_name, tier, rank1, league_points, wins, losses, updated_at) " +
                "VALUES (:queueType, :leagueId, :summonerId, :summonerName," +
                ":tier, :rank, :leaguePoints, :wins, :losses, :updatedAt) ON DUPLICATE KEY " +
               "UPDATE league_id = :leagueId, summoner_name = :summonerName, tier = :tier, rank1 = :rank, " +
               "league_points = :leaguePoints, wins = :wins, losses = :losses, updated_at = :updatedAt"
               , TABLE_LEAGUE);

       SqlParameterSource[] params = leagues
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

       namedParameterJdbcTemplate.batchUpdate(sql, params);
    }


}
