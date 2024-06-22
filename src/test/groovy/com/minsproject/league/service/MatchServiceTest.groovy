package com.minsproject.league.service

import com.minsproject.league.constant.status.MatchStatus
import com.minsproject.league.dto.MatchSearchDTO
import com.minsproject.league.dto.TeamSearchDTO
import com.minsproject.league.dto.response.MatchResponse
import com.minsproject.league.dto.response.TeamResponse
import com.minsproject.league.entity.Match
import com.minsproject.league.entity.Place
import com.minsproject.league.entity.Sports
import com.minsproject.league.entity.Team
import com.minsproject.league.repository.MatchRepository
import com.minsproject.league.repository.TeamMemberRepository
import com.minsproject.league.repository.TeamRepository
import com.minsproject.league.validator.MatchValidator
import spock.lang.Specification
import spock.lang.Subject


class MatchServiceTest extends Specification {

    TeamRepository teamRepository = Mock()

    TeamMemberRepository teamMemberRepository = Mock()

    MatchRepository matchRepository = Mock()

    MatchValidator matchValidator = Mock()

    @Subject
    def matchService = new MatchService(teamRepository, teamMemberRepository, matchRepository, matchValidator)

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
                new Match(matchId: 1L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.WAITING),
                new Match(matchId: 2L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.WAITING),
                new Match(matchId: 3L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.WAITING),
                new Match(matchId: 4L,  inviter: inviter, invitee: invitee, place: place, status: MatchStatus.WAITING),
        ]

        when:
        matchRepository.findFilteredMatchesByInviteeId(teamId, matchSearchDTO) >> expected

        then:
        def result = matchService.getReceivedMatchList(teamId, matchSearchDTO)
        result.size() == 4
        result.every {it instanceof MatchResponse }
    }
}