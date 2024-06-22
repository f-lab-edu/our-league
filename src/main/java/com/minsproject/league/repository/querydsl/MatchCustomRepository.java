package com.minsproject.league.repository.querydsl;

import com.minsproject.league.dto.MatchSearchDTO;
import com.minsproject.league.entity.Match;

import java.util.List;

public interface MatchCustomRepository {

    List<Match> findAllMatchesByInviteeId(Long teamId, Integer pageSize, Long offsetId);

    List<Match> findFilteredMatchesByInviteeId(Long teamId, MatchSearchDTO dto);
}
