package com.minsproject.league.service;

import com.minsproject.league.dto.TeamCreateDTO;
import com.minsproject.league.dto.TeamsDTO;
import com.minsproject.league.entity.Sports;
import com.minsproject.league.entity.Teams;
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
    public Page<TeamsDTO> getTeamList(Pageable pageable) {
        return teamsRepository.findAll(pageable).map(TeamsDTO::fromEntity);
    }

    @Override
    public void create(TeamCreateDTO dto) {
        Sports sports = sportsRepository.findById(dto.getSportsId()).orElseThrow();
        teamsRepository.save(Teams.fromDto(dto, sports));
    }
}
