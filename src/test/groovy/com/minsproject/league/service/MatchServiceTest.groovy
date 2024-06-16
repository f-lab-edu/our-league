package com.minsproject.league.service

import com.minsproject.league.dto.TeamSearchDTO
import com.minsproject.league.dto.response.TeamResponse
import com.minsproject.league.entity.Sports
import com.minsproject.league.entity.Team
import com.minsproject.league.repository.TeamRepository
import spock.lang.Specification
import spock.lang.Subject


class MatchServiceTest extends Specification {

    def teamRepository = Mock(TeamRepository)

    @Subject
    def matchService = new MatchService(teamRepository)

    def "매칭이 가능한 팀 조회해오기"() {
        given:
        TeamSearchDTO searchDTO = new TeamSearchDTO(10, 0L, "서울시", "강남구", "신사동", 1L)
        def sports = new Sports(sportsId: 1L, name: "축구")
        def teamEntities = [
                new Team(teamId: 1L, sports: sports),
                new Team(teamId: 2L, sports: sports)
        ]
        def teamResponses = teamEntities.collect { TeamResponse.fromEntity(it) }

        when:
        teamRepository.findTeamsForMatch(searchDTO) >> teamEntities
        List<TeamResponse> result = matchService.getTeamList(searchDTO)

        then:
        result == teamResponses
    }


}