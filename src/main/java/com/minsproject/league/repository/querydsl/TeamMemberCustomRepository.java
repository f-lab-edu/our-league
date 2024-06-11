package com.minsproject.league.repository.querydsl;

import com.minsproject.league.entity.TeamMember;

import java.util.Optional;

public interface TeamMemberCustomRepository {
    Optional<TeamMember> findByTeamIdAndUserId(Long teamId, Long userId);
}
