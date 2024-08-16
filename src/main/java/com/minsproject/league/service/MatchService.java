package com.minsproject.league.service;

import com.minsproject.league.dto.request.*;
import com.minsproject.league.dto.response.MatchResponse;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.entity.Place;
import com.minsproject.league.entity.Team;
import com.minsproject.league.entity.TeamMember;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.MatchRepository;
import com.minsproject.league.repository.TeamRepository;
import com.minsproject.league.validator.MatchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final TeamService teamService;

    private final TeamMemberService teamMemberService;

    private final PlaceService placeService;

    private final TeamRepository teamRepository;

    private final MatchRepository matchRepository;

    private final MatchValidator matchValidator;


    public List<TeamResponse> getTeamList(TeamSearchRequest searchDTO) {
        return teamRepository.findTeamsForMatch(searchDTO).stream().map(TeamResponse::fromEntity).toList();
    }

    @Transactional
    public Long createMatch(MatchRequest matchRequest, UserRequest userRequest) {

        validateMatchRequest(matchRequest);

        Place matchPlace = placeService.getPlace(matchRequest.getPlace());

        TeamMember teamMember = getTeamMemberAndCheckRole(matchRequest.getInviterTeamId(), userRequest.getUserId());

        Team inviter = getTeamAndCheckStatus(teamMember.getTeamMemberId());
        Team invitee = getTeamAndCheckStatus(matchRequest.getInviteeTeamId());

        return matchRepository.save(matchRequest.toEntity(inviter, invitee, matchPlace)).getMatchId();
    }

    public List<MatchResponse> getReceivedMatchList(MatchSearchRequest dto) {
        if (dto.isAllSearch()) {
            return getAllMatches(dto.getTeamId(), dto.getPageSize(), dto.getOffsetId());
        }

        return getFilteredMatches(dto.getTeamId(), dto);
    }

    private List<MatchResponse> getAllMatches(Long teamId, Integer pageSize, Long offsetId) {
        return matchRepository.findAllMatchesByInviteeId(teamId, pageSize, offsetId).stream().map(MatchResponse::fromEntity).toList();
    }

    private List<MatchResponse> getFilteredMatches(Long teamId, MatchSearchRequest dto) {
        return matchRepository.findFilteredMatchesByInviteeId(teamId, dto).stream().map(MatchResponse::fromEntity).toList();
    }

    private Team getTeamAndCheckStatus(Long teamId) {
        Team team = teamService.getTeam(teamId);

        if (!team.isAcceptingMatch()) {
            throw new LeagueCustomException(ErrorCode.TEAM_NOT_ACCEPTING_MATCHES);
        }

        return team;
    }

    private TeamMember getTeamMemberAndCheckRole(Long inviterTeamId, Long userId) {
        TeamMember teamMember = teamMemberService.findByTeamIdAndUserId(inviterTeamId, userId);

        if (teamMember.isNormalMember()) {
            throw new LeagueCustomException(ErrorCode.MATCH_INVITE_NOT_ALLOWED);
        }

        return teamMember;
    }

    private void validateMatchRequest(MatchRequest matchRequest) {
        if (matchValidator.isMatchDayBeforeNow(matchRequest.getMatchDay())) {
            throw new LeagueCustomException(ErrorCode.INVALID_MATCH_DAY);
        }

        if (matchValidator.isPlaceNotNull(matchRequest.getPlace())) {
            throw new LeagueCustomException(ErrorCode.INVALID_MATCH_PLACE);
        }
    }
}
