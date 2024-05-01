package com.minsproject.league.repository.querydsl;

import com.minsproject.league.entity.Teams;

import java.util.List;

public interface TeamsCustomRepository {

    List<Teams> findByTeamIdGreaterThanOffsetId(Long offsetId);
}
