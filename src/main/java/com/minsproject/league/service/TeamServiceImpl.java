package com.minsproject.league.service;

import com.minsproject.league.controller.request.TeamCreateRequest;
import com.minsproject.league.entity.Sports;
import com.minsproject.league.entity.Teams;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.SportsRepository;
import com.minsproject.league.repository.TeamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamsRepository teamsRepository;

    private final SportsRepository sportsRepository;

    @Override
    public Page<Teams> getTeamList(Pageable pageable) {
        return teamsRepository.findAll(pageable);
    }

    @Override
    public long create(TeamCreateRequest req) {
        Sports sports = sportsRepository.findById(req.getSportsId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.SPORTS_NOT_FOUND));
        return teamsRepository.save(Teams.fromDto(req, sports)).getTeamId();
    }
}
