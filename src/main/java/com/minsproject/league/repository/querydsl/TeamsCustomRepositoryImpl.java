package com.minsproject.league.repository.querydsl;

import com.minsproject.league.entity.QTeams;
import com.minsproject.league.entity.Teams;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamsCustomRepositoryImpl implements TeamsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public TeamsCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Teams> findByTeamIdGreaterThanOffsetId(Long offsetId) {
        return jpaQueryFactory
                .selectFrom(QTeams.teams)
                .where(QTeams.teams.teamId.gt(offsetId))
                .limit(10)
                .fetch();
    }
}
