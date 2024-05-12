package com.minsproject.league.service;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.entity.Team;
import com.minsproject.league.repository.TeamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamsRepository teamsRepository;

    public List<Team> getTeamList(TeamSearchDTO searchDTO) {
        return teamsRepository.findByTeamIdGreaterThanOffsetId(searchDTO);
    }
}
