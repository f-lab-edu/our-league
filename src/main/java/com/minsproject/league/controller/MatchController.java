package com.minsproject.league.controller;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/match")
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public List<TeamResponse> teamListForMatch(TeamSearchDTO teamSearchDTO) {
        return matchService.getTeamList(teamSearchDTO);
    }
}
