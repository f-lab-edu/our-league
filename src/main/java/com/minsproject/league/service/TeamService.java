package com.minsproject.league.service;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.dto.request.TeamCreateRequest;
import com.minsproject.league.entity.Sports;
import com.minsproject.league.entity.Team;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.SportsRepository;
import com.minsproject.league.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final SportsRepository sportsRepository;

    public List<Team> getTeamList(TeamSearchDTO searchDTO) {
        return teamRepository.findByTeamIdGreaterThanOffsetId(searchDTO);
    }

    public Long create(TeamCreateRequest request) {
        Sports sports = sportsRepository.findById(request.getSportsId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.SPORTS_NOT_FOUND));

        return teamRepository.save(Team.fromDto(request, sports)).getTeamId();
    }
}
