package com.minsproject.league.service

import com.minsproject.league.constant.TeamMemberRole
import com.minsproject.league.constant.UserRole
import com.minsproject.league.dto.TeamSearchDTO
import com.minsproject.league.dto.UserDTO
import com.minsproject.league.dto.request.TeamCreateRequest
import com.minsproject.league.dto.request.TeamModifyRequest
import com.minsproject.league.dto.response.TeamResponse
import com.minsproject.league.entity.Sports
import com.minsproject.league.entity.Team
import com.minsproject.league.entity.TeamMember
import com.minsproject.league.entity.User
import com.minsproject.league.exception.ErrorCode
import com.minsproject.league.exception.LeagueCustomException
import com.minsproject.league.repository.SportsRepository
import com.minsproject.league.repository.TeamMemberRepository
import com.minsproject.league.repository.TeamRepository
import spock.lang.Specification
import spock.lang.Subject


class TeamServiceTest extends Specification {

    def teamRepository = Mock(TeamRepository)

    def sportsRepository = Mock(SportsRepository)

    def teamMemberRepository = Mock(TeamMemberRepository)

    @Subject
    def teamService = new TeamService(teamRepository, sportsRepository, teamMemberRepository)

    def "TeamSearchDTO의 pageSize가 없으면 10을 기본값으로 잡는다"() {

        given: "pageSize가 null인 경우"
        def searchDTO = new TeamSearchDTO(null, 101)

        expect: "pageSize는 기본값인 10으로 잡혀야한다"
        10 == searchDTO.getPageSize()
    }

    def "TeamSearchDTO의 pageSize 매개변수가 있으면 받은 매개변수를 사용한다"() {

        given: "pageSize를 받는 경우"
        def searchDTO = new TeamSearchDTO(20, 101)

        expect: "pageSize는 20으로 잡혀야한다"
        20 == searchDTO.getPageSize()
    }

    def "getTeamList는 TeamSearchDTO를 기반으로 팀 목록을 반환한다"() {

        given:
        def sports = Sports.builder().sportsId(1).name("축구").build()
        def searchDTO = new TeamSearchDTO(2, 100)
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

    def "수정하려는 팀이 존재하지 않으면 예외 발생"() {
        given:
        def teamId = 999L
        def teamModifyRequest = new TeamModifyRequest(sportsId: 1L, teamName: "", description: "", dong: "", detailAddress: "", status: 1L, modifier: "")
        def userDTO = new UserDTO(userId: 1L, email: "", name: "", password:"", mobileNumber:"", socialLoginType: "", socialLoginId: "", role: UserRole.USER, status: 1)
        teamRepository.findById(teamId) >> Optional.empty()

        when:
        teamService.modify(teamId, teamModifyRequest, userDTO)

        then:
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.TEAM_NOT_FOUND
    }

    def "수정하려는 회원이 해당 팀의 OWNER 등급이 아니면 예외 발생"() {
        given:
        def teamId = 1L
        def teamModifyRequest = new TeamModifyRequest(sportsId: 1L)
        def userDTO = new UserDTO(userId: 1L)
        def sports = new Sports(sportsId: 1L, name: "축구", status: 1L)
        def team = new Team(teamId: 1L, sports: sports, teamName: "으라차FC")
        def user = new User(userId: 1L)
        def teamMember = new TeamMember(teamMemberId: 1L, role: TeamMemberRole.NORMAL)
        teamRepository.findById(teamId) >> Optional.of(team)
        teamMemberRepository.findByTeamIdAndUserId(teamId, user.getUserId()) >> Optional.of(teamMember)

        when:
        teamService.modify(teamId, teamModifyRequest, userDTO)

        then:
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.MODIFICATION_NOT_ALLOWED
    }

    def "수정하려는 종목이 존재하지 않는 종목이면 예외 발생"() {
        given:
        def teamId = 1L
        def teamModifyRequest = new TeamModifyRequest(sportsId: 1L)
        def userDTO = new UserDTO(userId: 1L)
        def sports = new Sports(sportsId: 1L, name: "축구", status: 1L)
        def team = new Team(teamId: 1L, sports: sports, teamName: "으라차FC")
        def user = new User(userId: 1L)
        def teamMember = new TeamMember(teamMemberId: 1L, role: TeamMemberRole.NORMAL)
        teamRepository.findById(teamId) >> Optional.of(team)
        teamMemberRepository.findByTeamIdAndUserId(teamId, user.getUserId()) >> Optional.of(teamMember)
        sportsRepository.findById(teamModifyRequest.sportsId) >> Optional.empty()

        when:
        teamService.modify(teamId, teamModifyRequest, userDTO)

        then:
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.SPORTS_NOT_FOUND
    }

}
