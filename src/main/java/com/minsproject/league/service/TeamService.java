package com.minsproject.league.service;

import com.minsproject.league.controller.request.TeamCreateRequest;
import com.minsproject.league.entity.Sports;
import com.minsproject.league.entity.Teams;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.SportsRepository;
import com.minsproject.league.repository.TeamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamsRepository teamsRepository;

    private final SportsRepository sportsRepository;

    public List<Teams> getTeamList(Long offsetId) {
        return teamsRepository.findByTeamIdGreaterThanOffsetId(offsetId);
    }

    public long create(TeamCreateRequest req) {
        Sports sports = sportsRepository.findById(req.getSportsId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.SPORTS_NOT_FOUND));
        return teamsRepository.save(Teams.fromDto(req, sports)).getTeamId();
    }
}
