package com.example.jdbc.team;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TeamRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(String name) {
        jdbcTemplate.update("INSERT INTO teams(name) values(?)", name);
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
