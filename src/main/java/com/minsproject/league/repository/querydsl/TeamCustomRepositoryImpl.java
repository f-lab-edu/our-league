package com.minsproject.league.repository.querydsl;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.minsproject.league.entity.QTeam.team;

@Repository
public class TeamCustomRepositoryImpl implements TeamCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public TeamCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Team> findByTeamIdGreaterThanOffsetId(TeamSearchDTO searchDTO) {
        return jpaQueryFactory
                .selectFrom(team)
                .where(team.teamId.gt(searchDTO.getOffsetId()))
                .limit(searchDTO.getPageSize())
                .fetch();
    }

    @Override
    public List<Team> findTeamsForMatch(TeamSearchDTO searchDTO) {
        return jpaQueryFactory
                .selectFrom(team)
                .where(
                        team.city.eq(searchDTO.getCity())
                        .and(team.town.eq(searchDTO.getTown()))
                        .and(team.dong.eq(searchDTO.getDong()))
                        .and(team.sports.sportsId.eq(searchDTO.getSportsId()))
                )
                .fetch()
                ;
    }

}
