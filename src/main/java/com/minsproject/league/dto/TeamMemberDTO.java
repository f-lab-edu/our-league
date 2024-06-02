package com.minsproject.league.dto;

import com.minsproject.league.constant.TeamMemberRole;
import com.minsproject.league.constant.status.TeamMemberStatus;
import com.minsproject.league.entity.Team;
import com.minsproject.league.entity.TeamMember;
import com.minsproject.league.entity.User;
import lombok.Getter;

@Getter
public class TeamMemberDTO {

    private Long teamMemberId;

    private Team team;

    private User user;

    private String role;

    private String status;


    public static TeamMember toEntity(Team team, User user, TeamMemberRole role, TeamMemberStatus status) {
        return TeamMember.builder()
                .team(team)
                .user(user)
                .role(role)
                .status(status)
                .build();
    }

}
