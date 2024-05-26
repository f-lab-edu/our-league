package com.minsproject.league.service

import com.minsproject.league.dto.TeamSearchDTO
import com.minsproject.league.dto.response.TeamResponse
import com.minsproject.league.entity.Sports
import com.minsproject.league.entity.Team
import com.minsproject.league.repository.TeamRepository
import spock.lang.Specification
import spock.lang.Subject

class TeamServiceTest extends Specification {

    def teamRepository = Mock(TeamRepository)

    @Subject
    def teamService = new TeamService(teamRepository)

    def "TeamSearchDTO의 pageSize가 없으면 10을 기본값으로 잡는다"() {

        given: "pageSize가 null인 경우"
        def searchDTO = TeamSearchDTO.of(null, 101)

        expect: "pageSize는 기본값인 10으로 잡혀야한다"
        10 == searchDTO.getPageSize()
    }

    def "TeamSearchDTO의 pageSize 매개변수가 있으면 받은 매개변수를 사용한다"() {

        given: "pageSize를 받는 경우"
        def searchDTO = TeamSearchDTO.of(20, 101)

        expect: "pageSize는 20으로 잡혀야한다"
        20 == searchDTO.getPageSize()
    }

    def "getTeamList는 TeamSearchDTO를 기반으로 팀 목록을 반환한다"() {

        given:
        def sports = Sports.builder().sportsId(1).name("축구").build()
        def searchDTO = TeamSearchDTO.of(2, 100)
        def teamEntities = [
                Team.builder()
                        .sports(sports).teamName("teamA").description("team").fullAddress("full Address").city("seoul").town("town").dong("dong").status(1).build(),
                Team.builder()
                        .sports(sports).teamName("teamA").description("team").fullAddress("full Address").city("seoul").town("town").dong("dong").status(1).build()
        ]
        def expectedResponse = teamEntities.collect() { TeamResponse.fromEntity(it) }

        and: "teamRepository가 searchDTO 조건에 맞는 팀 목록을 반환하도록 설정"
        teamRepository.findByTeamIdGreaterThanOffsetId(searchDTO) >> teamEntities

        when: "getTeamList 메서드를 호출"
        def result = teamService.getTeamList(searchDTO)

        then: "반환된 팀 목록이 예상 결과와 일치"
        result == expectedResponse

    }
}
