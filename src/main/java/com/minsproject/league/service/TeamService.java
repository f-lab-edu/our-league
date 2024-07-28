package com.minsproject.league.service;

import com.minsproject.league.constant.TeamMemberRole;
import com.minsproject.league.dto.request.TeamSearchRequest;
import com.minsproject.league.dto.request.UserRequest;
import com.minsproject.league.dto.request.TeamCreateRequest;
import com.minsproject.league.dto.request.TeamModifyRequest;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.entity.Team;
import com.minsproject.league.entity.TeamMember;
import com.minsproject.league.entity.User;
import com.minsproject.league.repository.TeamMemberRepository;
import com.minsproject.league.repository.TeamRepository;
import com.minsproject.league.entity.Sports;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final SportsService sportsService;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    public List<TeamResponse> getTeamList(TeamSearchRequest searchDTO) {
        return teamRepository.findByTeamIdGreaterThanOffsetId(searchDTO).stream().map(TeamResponse::fromEntity).toList();
    }

    public Long create(TeamCreateRequest request, UserRequest userRequest) {
        User user = userRepository.findById(userRequest.getUserId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.USER_NOT_FOUND));

        Sports sports = sportsService.getSportsById(request.getSportsId());
        Team saved = teamRepository.save(TeamCreateRequest.toEntity(request, sports));

        teamMemberRepository.save(TeamMember.createOwner(saved, user));

        return saved.getTeamId();
    }

    public TeamResponse modify(Long teamId, TeamModifyRequest request, UserRequest user) {
        Team team = getTeamOrThrow(teamId);

        TeamMember member = teamMemberRepository.findByTeamIdAndUserId(teamId, user.getUserId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        if (member.getRole() != TeamMemberRole.OWNER) {
            throw new LeagueCustomException(ErrorCode.MODIFICATION_NOT_ALLOWED);
        }

        Sports sports = sportsService.getSportsById(request.getSportsId());

        team.modifyTeam(request, sports, user.getName());

        return TeamResponse.fromEntity(teamRepository.save(team));
    }

    public void delete(Long teamId, UserRequest user) {
        Team team = getTeamOrThrow(teamId);

        TeamMember member = teamMemberRepository.findByTeamIdAndUserId(teamId, user.getUserId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        if (member.getRole() != TeamMemberRole.OWNER) {
            throw new LeagueCustomException(ErrorCode.DELETING_NOT_ALLOWED);
        }

        team.delete();

        teamRepository.save(team);
    }

    private Team getTeamOrThrow(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_NOT_FOUND));
    }
}
