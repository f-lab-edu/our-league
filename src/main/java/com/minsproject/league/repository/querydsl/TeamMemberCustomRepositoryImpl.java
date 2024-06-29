package com.minsproject.league.repository.querydsl;

import com.minsproject.league.entity.TeamMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;

import static com.minsproject.league.entity.QTeamMember.teamMember;

public class TeamMemberCustomRepositoryImpl implements TeamMemberCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public TeamMemberCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<TeamMember> findByTeamIdAndUserId(Long teamId, Long userId) {
        TeamMember result = jpaQueryFactory
                .selectFrom(teamMember)
                .where(teamMember.team.teamId.eq(teamId)
                        .and(teamMember.user.userId.eq(userId)))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
