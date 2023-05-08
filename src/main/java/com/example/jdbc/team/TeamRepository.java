package com.example.jdbc.team;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TeamRepository {

    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder;

    public TeamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolder = new GeneratedKeyHolder();
    }

    public Long save(String name) {
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO teams(name) values (?)",
                    new String[]{"team_id"}
            );
            pstmt.setString(1, name);
            return pstmt;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Team> findById(Long teamId) {
        List<Team> results = jdbcTemplate.query("SELECT * FROM teams WHERE teams.team_id = ?", mapper, teamId);
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    public List<Team> findAll() {
        return jdbcTemplate.query("SELECT * FROM teams", mapper);
    }

    private static RowMapper<Team> mapper = (rs, rowNum) ->
            new Team(rs.getLong("team_id"), rs.getString("name"));
}
