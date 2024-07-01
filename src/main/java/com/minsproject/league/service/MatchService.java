package com.minsproject.league.service;

import com.minsproject.league.constant.status.MatchStatus;
import com.minsproject.league.dto.*;
import com.minsproject.league.dto.response.MatchResponse;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.entity.Match;
import com.minsproject.league.entity.Result;
import com.minsproject.league.entity.Team;
import com.minsproject.league.entity.TeamMember;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.MatchRepository;
import com.minsproject.league.repository.ResultRepository;
import com.minsproject.league.repository.TeamMemberRepository;
import com.minsproject.league.repository.TeamRepository;
import com.minsproject.league.validator.MatchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final TeamService teamService;

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final MatchRepository matchRepository;

    private final ResultRepository resultRepository;

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

        if (match.getStatus() != MatchStatus.PENDING) {
            throw new LeagueCustomException(ErrorCode.MATCH_CANNOT_BE_CANCELED);
        }

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

    public Long addResult(Long matchId, Long teamId, String result, Long userId) {
        // 존재하는 매치인지
        Match match = getMatch(matchId);

        // 매칭에 참여한 팀이 맞는지
        boolean isInviter = match.isInviter(teamId);
        boolean isInvitee = match.isInvitee(teamId);
        if (!(isInviter || isInvitee)) {
            throw new LeagueCustomException(ErrorCode.MATCH_INVALID_TEAM);
        }

        // 유저가 해당 팀의 owner가 맞는지
        Long userTeamId = isInviter ? match.getInviter().getTeamId() : match.getInvitee().getTeamId();
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(userTeamId, userId).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        if (!teamMember.isOwner()) {
            throw new LeagueCustomException(ErrorCode.MATCH_RESULT_NOT_ALLOWED);
        }

        // 매치의 상태가 ACCEPTED인지
        if (match.getStatus() != MatchStatus.ACCEPTED) {
            throw new LeagueCustomException(ErrorCode.MATCH_STATUS_NOT_ACCEPTED);
        }

        // 매칭 날짜가 지났는지
        if (match.getMatchDay().isBefore(LocalDateTime.now())) {
            throw new LeagueCustomException(ErrorCode.MATCH_DAY_MUST_BE_PASSED);
        }

        // 해당팀이 이미 등록한 결과가 있는지
        boolean hasResult = resultRepository.findByMatchIdAndTeamId(matchId, userTeamId).isPresent();
        if (hasResult) {
            throw new LeagueCustomException(ErrorCode.MATCH_RESULT_BY_TEAM_EXISTS);
        }

        //Result 엔티티 생성 및 저장, 저장된 엔티티 id 값 리턴
        Team team = isInviter ? match.getInviter() : match.getInvitee();
        return resultRepository.save(Result.of(match, team, result)).getResultId();
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
