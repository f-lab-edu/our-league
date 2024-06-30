package com.minsproject.league.service;

import com.minsproject.league.constant.status.MatchStatus;
import com.minsproject.league.dto.MatchDTO;
import com.minsproject.league.dto.MatchSearchDTO;
import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.dto.UserDTO;
import com.minsproject.league.dto.response.MatchResponse;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.entity.Match;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final TeamService teamService;

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final MatchRepository matchRepository;

    private final MatchValidator matchValidator;

    public List<TeamResponse> getTeamList(TeamSearchDTO searchDTO) {
        return teamRepository.findTeamsForMatch(searchDTO).stream().map(TeamResponse::fromEntity).toList();
    }

    public Long createMatch(MatchDTO matchDTO, UserDTO userDTO) {

        matchValidator.validateMatchDay(matchDTO.getMatchDay());

        matchValidator.validatePlace(matchDTO.getPlace());

        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(matchDTO.getInviterTeamId(), userDTO.getUserId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        matchValidator.validateTeamMemberRole(teamMember);

        Team inviter = teamMember.getTeam();
        Team invitee = teamRepository.findById(matchDTO.getInviteeTeamId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_NOT_FOUND));

        matchValidator.validateTeamAddress(inviter.getFullAddress(), invitee.getFullAddress());

        matchValidator.validateTeamStatus(invitee.getStatus());

        return matchRepository.save(MatchDTO.toEntity(inviter, invitee, matchDTO)).getMatchId();
    }

    public List<MatchResponse> getReceivedMatchList(Long teamId, MatchSearchDTO dto) {
        if (isAllSearch(dto)) {
            return getAllMatches(teamId, dto.getPageSize(), dto.getOffsetId());
        }

        return getFilteredMatches(teamId, dto);
    }

    public MatchResponse getMatchDetail(Long matchId) {
        return matchRepository.findById(matchId).map(MatchResponse::fromEntity).orElseThrow(() -> new LeagueCustomException(ErrorCode.MATCH_NOT_FOUND));
    }

    @Transactional
    public Long acceptMatch(Long matchId) {
        Match match = getMatch(matchId);

        matchValidator.validateAcceptableMatch(match.getStatus());

        matchValidator.validateMatchDay(match.getMatchDay());

        Team inviter = teamService.getTeam(match.getInviter().getTeamId());
        Team invitee = teamService.getTeam(match.getInvitee().getTeamId());

        // 신청한 팀이 매칭이 가능한 상태인지 확인
        matchValidator.validateTeamStatus(inviter.getStatus());

        // 수락하려는 팀이 매칭이 가능한 상태인지 확인
        matchValidator.validateTeamStatus(invitee.getStatus());

        match.setStatus(MatchStatus.ACCEPTED);

        return matchRepository.save(match).getMatchId();
    }

    public Long rejectMatch(Long matchId, Long userId) {
        Match match = getMatch(matchId);

        Team invitee = match.getInvitee();
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(invitee.getTeamId(), userId)
                .orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        if (!teamMember.isOwner()) {
            throw new LeagueCustomException(ErrorCode.MATCH_REJECT_NOT_ALLOWED);
        }

        match.setStatus(MatchStatus.REJECTED);

        return matchRepository.save(match).getMatchId();
    }

    public Long cancelMatch(Long matchId, Long userId) {
        Match match = getMatch(matchId);

        if (match.getStatus() != MatchStatus.PENDING) {
            throw new LeagueCustomException(ErrorCode.MATCH_CANNOT_BE_CANCELED);
        }

        Team inviter = match.getInviter();
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(inviter.getTeamId(), userId)
                .orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        if (!teamMember.isOwner()) {
            throw new LeagueCustomException(ErrorCode.MATCH_REJECT_NOT_ALLOWED);
        }

        match.setStatus(MatchStatus.CANCELED);

        return matchRepository.save(match).getMatchId();
    }

    public Match getMatch(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new LeagueCustomException(ErrorCode.MATCH_NOT_FOUND));
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
