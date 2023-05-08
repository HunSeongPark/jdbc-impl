package com.example.jdbc.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void createTeam(String name) {
        teamRepository.save(name);
    }

    public Team findById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("team not found"));
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}
