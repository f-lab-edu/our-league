package com.minsproject.league.service;

import com.minsproject.league.constant.TeamMemberRole;
import com.minsproject.league.constant.status.TeamMemberStatus;
import com.minsproject.league.constant.status.TeamStatus;
import com.minsproject.league.dto.TeamMemberDTO;
import com.minsproject.league.dto.UserDTO;
import com.minsproject.league.dto.response.TeamMemberResponse;
import com.minsproject.league.entity.Team;
import com.minsproject.league.entity.TeamMember;
import com.minsproject.league.entity.User;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.TeamMemberRepository;
import com.minsproject.league.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final UserService userService;

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    public Long create(Long teamId, UserDTO user) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_NOT_FOUND));
        if (team.getStatus() == TeamStatus.PAUSED) {
            throw new LeagueCustomException(ErrorCode.TEAM_NOT_ACCEPTING_MEMBER);
        }

        List<TeamMember> teamMembers = team.getTeamMembers();
        boolean isUserInTeam = teamMembers.stream().anyMatch(member -> Objects.equals(member.getUser().getUserId(), user.getUserId()));
        if (isUserInTeam) {
            throw new LeagueCustomException(ErrorCode.ALREADY_IN_TEAM);
        }

        User userInfo = userService.getUserById(user.getUserId());

        TeamMember saved = teamMemberRepository.save(TeamMemberDTO.toEntity(team, userInfo, TeamMemberRole.NORMAL, TeamMemberStatus.NORMAL));

        return saved.getTeamMemberId();
    }

    public TeamMemberResponse modify(TeamMemberDTO teamMemberDTO, UserDTO userDTO) {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberDTO.getTeamMemberId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        if (teamMember.isOwnerOrMyself(userDTO.getUserId())) {
            teamMember.modify(teamMemberDTO);
            return TeamMemberResponse.fromEntity(teamMemberRepository.save(teamMember));
        }

        throw new LeagueCustomException(ErrorCode.MODIFICATION_NOT_ALLOWED);
    }

    @Transactional
    public void delete(Long teamMemberId, UserDTO userDTO) {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId).orElseThrow(() -> new LeagueCustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        if (teamMember.isOwnerOrMyself(userDTO.getUserId())) {
            teamMember.delete();
            teamMemberRepository.save(teamMember);
            return;
        }

        throw new LeagueCustomException(ErrorCode.MODIFICATION_NOT_ALLOWED);
    }
}
