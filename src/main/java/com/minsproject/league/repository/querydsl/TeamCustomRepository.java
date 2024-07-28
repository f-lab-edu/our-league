package com.minsproject.league.repository.querydsl;

import com.minsproject.league.dto.request.TeamSearchRequest;
import com.minsproject.league.entity.Team;

import java.util.List;

public interface TeamCustomRepository {

    List<Team> findByTeamIdGreaterThanOffsetId(TeamSearchRequest searchDTO);

    List<Team> findTeamsForMatch(TeamSearchRequest searchDTO);
}
