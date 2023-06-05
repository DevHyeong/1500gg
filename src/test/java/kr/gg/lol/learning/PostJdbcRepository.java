package kr.gg.lol.learning;

import kr.gg.lol.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void bulkInsert(List<Post> posts){
        String sql = "INSERT INTO POST (user_id, title, content, created_at) " +
                "VALUES (:userId, :title, :content, :createdAt)";

        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(sql, params);
    }
}
