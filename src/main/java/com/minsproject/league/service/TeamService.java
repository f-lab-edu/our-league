package com.minsproject.league.service;

import com.minsproject.league.constant.TeamMemberRole;
import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.dto.UserDTO;
import com.minsproject.league.dto.request.TeamCreateRequest;
import com.minsproject.league.dto.request.TeamModifyRequest;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.entity.Team;
import com.minsproject.league.entity.TeamMember;
import com.minsproject.league.repository.TeamMemberRepository;
import com.minsproject.league.repository.TeamRepository;
import com.minsproject.league.entity.Sports;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.SportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final SportsRepository sportsRepository;
    private final TeamMemberRepository teamMemberRepository;

    public List<TeamResponse> getTeamList(TeamSearchDTO searchDTO) {
        return teamRepository.findByTeamIdGreaterThanOffsetId(searchDTO).stream().map(TeamResponse::fromEntity).toList();
    }

    public Long create(TeamCreateRequest request) {
        Sports sports = sportsRepository.findById(request.getSportsId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.SPORTS_NOT_FOUND));

        return teamRepository.save(TeamCreateRequest.toEntity(request, sports)).getTeamId();
    }

    public TeamResponse modify(Long teamId, TeamModifyRequest request, UserDTO user) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_NOT_FOUND));

        TeamMember member = teamMemberRepository.findByTeamIdAndUserId(team, UserDTO.toEntity(user)).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        if (member.getRole() != TeamMemberRole.OWNER) {
            throw new LeagueCustomException(ErrorCode.MODIFICATION_NOT_ALLOWED);
        }

        Sports sports = sportsRepository.findById(request.getSportsId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.SPORTS_NOT_FOUND));

        team.modifyTeam(request, sports, user.getName());

        return TeamResponse.fromEntity(teamRepository.save(team));
    }
}
