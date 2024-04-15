package com.minsproject.league.service;

import com.minsproject.league.controller.request.TeamCreateRequest;
import com.minsproject.league.dto.TeamCreateDTO;
import com.minsproject.league.dto.TeamsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {

    Page<TeamsDTO> getTeamList(Pageable pageable);

    void create(TeamCreateDTO dto);
}
