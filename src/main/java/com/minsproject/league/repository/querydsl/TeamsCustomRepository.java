package com.minsproject.league.repository.querydsl;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.entity.Team;

import java.util.List;

public interface TeamsCustomRepository {

    List<Team> findByTeamIdGreaterThanOffsetId(TeamSearchDTO searchDTO);
}
