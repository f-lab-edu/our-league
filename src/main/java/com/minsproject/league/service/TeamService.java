package com.minsproject.league.service;

import com.minsproject.league.controller.request.TeamCreateRequest;
import com.minsproject.league.entity.Teams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TeamService {

    Page<Teams> getTeamList(Pageable pageable);

    long create(TeamCreateRequest req);

}
