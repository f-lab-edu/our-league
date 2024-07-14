package com.minsproject.league.service;

import com.minsproject.league.dto.*;
import com.minsproject.league.dto.response.MatchResponse;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.entity.Place;
import com.minsproject.league.entity.PlaceRepository;
import com.minsproject.league.entity.Team;
import com.minsproject.league.entity.TeamMember;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.MatchRepository;
import com.minsproject.league.repository.TeamMemberRepository;
import com.minsproject.league.repository.TeamRepository;
import com.minsproject.league.validator.MatchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final MatchRepository matchRepository;

    private final PlaceRepository placeRepository;

    private final MatchValidator matchValidator;


    public List<TeamResponse> getTeamList(TeamSearchDTO searchDTO) {
        return teamRepository.findTeamsForMatch(searchDTO).stream().map(TeamResponse::fromEntity).toList();
    }

    public Long createMatch(MatchDTO matchDTO, UserDTO userDTO) {

        if (matchValidator.isMatchDayBeforeNow(matchDTO.getMatchDay())) {
            throw new LeagueCustomException(ErrorCode.INVALID_MATCH_DAY);
        }

        if (matchValidator.isPlaceNotNull(matchDTO.getPlace())) {
            throw new LeagueCustomException(ErrorCode.INVALID_MATCH_PLACE);
        }

        boolean isNewPlace = matchDTO.getPlace().isNewPlace();
        Place matchPlace;
        if (isNewPlace) {
            matchPlace = placeRepository.save(matchDTO.getPlace().toEntity());
        } else {
            matchPlace = placeRepository.findById(matchDTO.getPlace().getPlaceId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.INVALID_MATCH_PLACE));
        }

        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(matchDTO.getInviterTeamId(), userDTO.getUserId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        if (teamMember.isNormalMember()) {
            throw new LeagueCustomException(ErrorCode.MATCH_INVITE_NOT_ALLOWED);
        }

        Team inviter = teamRepository.findById(teamMember.getTeam().getTeamId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_NOT_FOUND));
        if (!inviter.isAcceptingMatch()) {
            throw new LeagueCustomException(ErrorCode.TEAM_NOT_ACCEPTING_MATCHES);
        }

        Team invitee = teamRepository.findById(matchDTO.getInviteeTeamId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_NOT_FOUND));
        if (!invitee.isAcceptingMatch()) {
            throw new LeagueCustomException(ErrorCode.TEAM_NOT_ACCEPTING_MATCHES);
        }

        return matchRepository.save(matchDTO.toEntity(inviter, invitee, matchPlace)).getMatchId();
    }

    public List<MatchResponse> getReceivedMatchList(Long teamId, MatchSearchDTO dto) {
        if (isAllSearch(dto)) {
            return getAllMatches(teamId, dto.getPageSize(), dto.getOffsetId());
        }

        return getFilteredMatches(teamId, dto);
    }

    private List<MatchResponse> getAllMatches(Long teamId, Integer pageSize, Long offsetId) {
        return matchRepository.findAllMatchesByInviteeId(teamId, pageSize, offsetId).stream().map(MatchResponse::fromEntity).toList();
    }

    private List<MatchResponse> getFilteredMatches(Long teamId, MatchSearchDTO dto) {
        return matchRepository.findFilteredMatchesByInviteeId(teamId, dto).stream().map(MatchResponse::fromEntity).toList();
    }

    private boolean isAllSearch(MatchSearchDTO dto) {
        return dto.getStatus() == null
                && dto.getStartDate() == null
                && dto.getEndDate() == null;
    }
}
