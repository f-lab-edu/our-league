package com.minsproject.league.service

import com.minsproject.league.constant.status.MatchStatus
import com.minsproject.league.constant.status.TeamStatus
import com.minsproject.league.dto.MatchSearchDTO
import com.minsproject.league.dto.TeamSearchDTO
import com.minsproject.league.dto.response.MatchResponse
import com.minsproject.league.dto.response.TeamResponse
import com.minsproject.league.entity.Match
import com.minsproject.league.entity.Place
import com.minsproject.league.entity.Sports
import com.minsproject.league.entity.Team
import com.minsproject.league.exception.ErrorCode
import com.minsproject.league.exception.LeagueCustomException
import com.minsproject.league.repository.MatchRepository
import com.minsproject.league.repository.TeamMemberRepository
import com.minsproject.league.repository.TeamRepository
import com.minsproject.league.validator.MatchValidator
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime


class MatchServiceTest extends Specification {

    TeamService teamService = Mock()

    TeamRepository teamRepository = Mock()

    TeamMemberRepository teamMemberRepository = Mock()

    MatchRepository matchRepository = Mock()

    MatchValidator matchValidator = Mock()

    @Subject
    def matchService = new MatchService(teamService, teamRepository, teamMemberRepository, matchRepository, matchValidator)

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

    def "검색 조건이 없는 경우"() {
        given:
        def teamId = 1L
        def matchSearchDTO = new MatchSearchDTO(pageSize: 10, offsetId: 0L)
        def inviter = new Team(teamId: 1L)
        def invitee = new Team(teamId: 2L)
        def place = new Place(placeId: 1L)
        def expected = [
                new Match(matchId: 1L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING),
                new Match(matchId: 2L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING),
                new Match(matchId: 3L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING),
                new Match(matchId: 4L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING),
        ]

        when:
        matchRepository.findAllMatchesByInviteeId(teamId, matchSearchDTO.pageSize, matchSearchDTO.offsetId) >> expected

        then:
        def result = matchService.getReceivedMatchList(teamId, matchSearchDTO)
        result.size() == 4
        result.every { it instanceof MatchResponse }
    }

    def "검색 조건이 있는 경우"() {
        given:
        def teamId = 1L
        def matchSearchDTO = new MatchSearchDTO(pageSize: 10, offsetId: 0L, status: "WAITING", startDate: null, endDate: null)
        def inviter = new Team(teamId: 1L)
        def invitee = new Team(teamId: 2L)
        def place = new Place(placeId: 1L)
        def expected = [
                new Match(matchId: 1L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING),
                new Match(matchId: 2L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING),
                new Match(matchId: 3L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING),
                new Match(matchId: 4L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING),
        ]

        when:
        matchRepository.findFilteredMatchesByInviteeId(teamId, matchSearchDTO) >> expected

        then:
        def result = matchService.getReceivedMatchList(teamId, matchSearchDTO)
        result.size() == 4
        result.every {it instanceof MatchResponse }
    }

    def "상세 내용을 보려는 매치가 있으면 MatchResponse가 반환된다"() {
        given:
        def matchId = 1L
        def inviter = new Team(teamId: 1L)
        def invitee = new Team(teamId: 2L)
        def place = new Place(placeId: 1L)
        def match = new Match(matchId: 1L, inviter: inviter, invitee: invitee, place: place, status: MatchStatus.PENDING)

        when:
        def result = matchService.getMatchDetail(matchId)

        then:
        1 * matchRepository.findById(matchId) >> Optional.of(match)
        result instanceof MatchResponse
    }

    def "매칭을 찾을 수 없으면 LeagueCustomException이 발생한다"() {
        given:
        def matchId = 321L

        when:
        matchService.getMatchDetail(matchId)

        then:
        1 * matchRepository.findById(matchId) >> Optional.empty()
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.MATCH_NOT_FOUND
    }

    def "존재하지 않는 매칭으로 매칭 수락 실패"() {
        given:
        def matchId = 999L

        when:
        matchService.acceptMatch(matchId)

        then:
        1 * matchRepository.findById(matchId) >> Optional.empty()
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.MATCH_NOT_FOUND
    }

    def "매칭 상태값이 PEDNING아 아니여서 매칭 수락 실패"() {
        given:
        def matchId = 1L
        def finished = MatchStatus.FINISHED
        def match = new Match(matchId: matchId, status: finished)

        when:
        matchService.acceptMatch(matchId)

        then:
        1 * matchRepository.findById(matchId) >> Optional.of(match)
        1 * matchValidator.validateAcceptableMatch(match.getStatus()) >> { throw new LeagueCustomException(ErrorCode.MATCH_STATUS_NOT_PENDING) }
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.MATCH_STATUS_NOT_PENDING
    }

    def "매칭 날짜가 지나서 매칭 수락 실패"() {
        given:
        def matchId = 1L
        def finished = MatchStatus.FINISHED
        def matchDay = LocalDateTime.now().minusDays(5)
        def match = new Match(matchId: matchId, status: finished, matchDay: matchDay)

        when:
        matchService.acceptMatch(matchId)

        then:
        1 * matchRepository.findById(matchId) >> Optional.of(match)
        1 * matchValidator.validateAcceptableMatch(match.getStatus())
        1 * matchValidator.validateMatchDay(match.getMatchDay()) >> { throw new LeagueCustomException(ErrorCode.INVALID_MATCH_DAY)}
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.INVALID_MATCH_DAY
    }

    def "신청자 팀이 매칭 가능 상태가 아니여서 매칭 수락 실패"() {
        given:
        def matchId = 1L
        def finished = MatchStatus.FINISHED
        def matchDay = LocalDateTime.now().minusDays(5)
        def inviter = new Team(teamId: 1L, status: TeamStatus.PAUSED)
        def invitee = new Team(teamId: 2L, status: TeamStatus.ACCEPTING)
        def match = new Match(matchId: matchId, status: finished, matchDay: matchDay, inviter: inviter, invitee: invitee)

        when:
        matchService.acceptMatch(matchId)

        then:
        1 * matchRepository.findById(matchId) >> Optional.of(match)
        1 * matchValidator.validateAcceptableMatch(match.getStatus())
        1 * matchValidator.validateMatchDay(match.getMatchDay())
        1 * teamService.getTeam(match.getInviter().getTeamId()) >> inviter
        1 * teamService.getTeam(match.getInvitee().getTeamId()) >> invitee
        1 * matchValidator.validateTeamStatus(inviter.getStatus()) >> { throw new LeagueCustomException(ErrorCode.TEAM_NOT_ACCEPTING_MATCHES)}
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.TEAM_NOT_ACCEPTING_MATCHES
    }

    def "신청 받은 팀이 매칭 가능 상태가 아니여서 매칭 수락 실패"() {
        given:
        def matchId = 1L
        def finished = MatchStatus.FINISHED
        def matchDay = LocalDateTime.now().minusDays(5)
        def inviter = new Team(teamId: 1L, status: TeamStatus.PAUSED)
        def invitee = new Team(teamId: 2L, status: TeamStatus.ACCEPTING)
        def match = new Match(matchId: matchId, status: finished, matchDay: matchDay, inviter: inviter, invitee: invitee)

        when:
        matchService.acceptMatch(matchId)

        then:
        1 * matchRepository.findById(matchId) >> Optional.of(match)
        1 * matchValidator.validateAcceptableMatch(match.getStatus())
        1 * matchValidator.validateMatchDay(match.getMatchDay())
        1 * teamService.getTeam(match.getInviter().getTeamId()) >> inviter
        1 * teamService.getTeam(match.getInvitee().getTeamId()) >> invitee
        1 * matchValidator.validateTeamStatus(inviter.getStatus())
        1 * matchValidator.validateTeamStatus(invitee.getStatus()) >> { throw new LeagueCustomException(ErrorCode.TEAM_NOT_ACCEPTING_MATCHES)}
        def exception = thrown(LeagueCustomException)
        exception.errorCode == ErrorCode.TEAM_NOT_ACCEPTING_MATCHES
    }

    def "매칭 수락 성공"() {
        given:
        def matchId = 1L
        def finished = MatchStatus.FINISHED
        def matchDay = LocalDateTime.now().minusDays(5)
        def inviter = new Team(teamId: 1L, status: TeamStatus.PAUSED)
        def invitee = new Team(teamId: 2L, status: TeamStatus.ACCEPTING)
        def match = new Match(matchId: matchId, status: finished, matchDay: matchDay, inviter: inviter, invitee: invitee)

        when:
        matchService.acceptMatch(matchId)

        then:
        1 * matchRepository.findById(matchId) >> Optional.of(match)
        1 * matchValidator.validateAcceptableMatch(match.getStatus())
        1 * matchValidator.validateMatchDay(match.getMatchDay())
        1 * teamService.getTeam(match.getInviter().getTeamId()) >> inviter
        1 * teamService.getTeam(match.getInvitee().getTeamId()) >> invitee
        1 * matchValidator.validateTeamStatus(inviter.getStatus())
        1 * matchValidator.validateTeamStatus(invitee.getStatus())

        match.getStatus() == MatchStatus.ACCEPTED
        1 * matchRepository.save(match) >> match
    }
}