package com.minsproject.league.service;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.repository.TeamRepository;
import com.minsproject.league.entity.Sports;
import com.minsproject.league.entity.Team;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.SportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final SportsRepository sportsRepository;

    public List<TeamResponse> getTeamList(TeamSearchDTO searchDTO) {
        return teamRepository.findByTeamIdGreaterThanOffsetId(searchDTO).stream().map(TeamResponse::fromEntity).toList();
        return teamRepository.findByTeamIdGreaterThanOffsetId(searchDTO).stream().map(TeamResponse::fromEntity).toList();
    }

    public Long create(TeamCreateRequest request) {
        Sports sports = sportsRepository.findById(request.getSportsId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.SPORTS_NOT_FOUND));

        return teamRepository.save(Team.fromDto(request, sports)).getTeamId();
    }
}
