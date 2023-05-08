package com.example.jdbc.team;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/teams")
@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Void> createTeam(@RequestParam String name) {
        teamService.createTeam(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> findById(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.findById(teamId));
    }

    @GetMapping
    public ResponseEntity<List<Team>> findAll() {
        return ResponseEntity.ok(teamService.findAll());
    }
}
