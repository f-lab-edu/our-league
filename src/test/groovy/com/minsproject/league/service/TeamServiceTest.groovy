package com.minsproject.league.service

import com.minsproject.league.dto.TeamSearchDTO
import com.minsproject.league.dto.request.TeamCreateRequest
import com.minsproject.league.dto.response.TeamResponse
import com.minsproject.league.entity.Sports
import com.minsproject.league.entity.Team
import com.minsproject.league.exception.ErrorCode
import com.minsproject.league.exception.LeagueCustomException
import com.minsproject.league.repository.SportsRepository
import com.minsproject.league.repository.TeamRepository
import spock.lang.Specification
import spock.lang.Subject

class TeamServiceTest extends Specification {

    def teamRepository = Mock(TeamRepository)

    def sportsRepository = Mock(SportsRepository)

    @Subject
    def teamService = new TeamService(teamRepository, sportsRepository)

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

    def "팀 등록 시 존재하지 않는 종목이면 예외가 발생한다"() {

        given:
        def teamCreateReq = TeamCreateRequest.builder().sportsId(999).build()
        sportsRepository.findById(teamCreateReq.sportsId) >> Optional.empty()

        when:
        teamService.create(teamCreateReq)

        then:
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.SPORTS_NOT_FOUND

    }

    def "팀 등록 성공"() {

        given:
        def teamCreateReq = TeamCreateRequest.builder()
                .sportsId(1L)
                .teamName("test Team")
                .description("")
                .city("서울시")
                .town("마포구")
                .dong("합정동")
                .detailAddress("123-1")
                .fullAddress("서울시 마포구 합정동 123-1")
                .build()
        def sports = Sports.builder().sportsId(1L).name("축구").build()
        def team = Team.builder().teamId(1L).build()

        sportsRepository.findById(teamCreateReq.sportsId) >> Optional.of(sports)
        teamRepository.save(_ as Team) >> team

        when:
        def teamId = teamService.create(teamCreateReq)

        then:
        teamId == 1L
    }
}
