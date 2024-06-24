package com.minsproject.league.repository.querydsl;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.entity.QTeam;
import com.minsproject.league.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamCustomRepositoryImpl implements TeamCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public TeamCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Team> findByTeamIdGreaterThanOffsetId(TeamSearchDTO searchDTO) {
        return jpaQueryFactory
                .selectFrom(QTeam.team)
                .where(QTeam.team.teamId.gt(searchDTO.getOffsetId()))
                .limit(searchDTO.getPageSize())
                .fetch();
    }

    @Override
    public List<Team> findTeamsForMatch(TeamSearchDTO searchDTO) {
        return jpaQueryFactory
                .selectFrom(QTeam.team)
                .where(
                        QTeam.team.city.eq(searchDTO.getCity())
                        .and(QTeam.team.town.eq(searchDTO.getTown()))
                        .and(QTeam.team.dong.eq(searchDTO.getDong()))
                        .and(QTeam.team.sports.sportsId.eq(searchDTO.getSportsId()))
                )
                .fetch()
                ;
    }

}
