package com.minsproject.league.service;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final TeamRepository teamRepository;

    public List<TeamResponse> getTeamList(TeamSearchDTO searchDTO) {
        return teamRepository.findTeamsForMatch(searchDTO).stream().map(TeamResponse::fromEntity).toList();
    }
}
