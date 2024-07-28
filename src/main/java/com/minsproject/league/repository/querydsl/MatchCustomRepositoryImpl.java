package com.minsproject.league.repository.querydsl;

import com.minsproject.league.constant.status.MatchStatus;
import com.minsproject.league.dto.request.MatchSearchRequest;
import com.minsproject.league.entity.Match;
import com.minsproject.league.entity.QMatch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.List;

public class MatchCustomRepositoryImpl implements MatchCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MatchCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Match> findAllMatchesByInviteeId(Long teamId, Integer pageSize, Long offsetId) {
        return jpaQueryFactory
                .selectFrom(QMatch.match)
                .where(
                        QMatch.match.invitee.teamId.eq(teamId)
                        .and(QMatch.match.matchId.gt(offsetId))
                )
                .orderBy(QMatch.match.matchId.desc())
                .limit(pageSize)
                .fetch();
    }

    @Override
    public List<Match> findFilteredMatchesByInviteeId(Long teamId, MatchSearchRequest dto) {
        return jpaQueryFactory
                .selectFrom(QMatch.match)
                .where(QMatch.match.invitee.teamId.eq(teamId)
                        .and(QMatch.match.matchId.gt(dto.getOffsetId()))
                        , statusEq(dto.getStatus())
                        , startDateGoe(dto.getStartDate())
                        , endDateLoe(dto.getEndDate())
                )
                .orderBy(QMatch.match.matchId.desc())
                .limit(dto.getPageSize())
                .fetch();
    }

    private BooleanExpression statusEq(String status) {
        return status == null ? null : QMatch.match.status.eq(MatchStatus.valueOf(status));
    }

    private BooleanExpression startDateGoe(LocalDateTime startDate) {
        return startDate == null ? null : QMatch.match.matchDay.goe(startDate);
    }

    private BooleanExpression endDateLoe(LocalDateTime endDate) {
        return endDate == null ? null : QMatch.match.matchDay.loe((endDate));
    }
}
