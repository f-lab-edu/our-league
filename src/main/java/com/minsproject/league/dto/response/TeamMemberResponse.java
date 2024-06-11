package com.minsproject.league.dto.response;

import com.minsproject.league.entity.TeamMember;

public class TeamMemberResponse {

    private Long teamMemberId;

    private String teamName;

    private String role;

    private String status;

    private TeamMemberResponse(Long teamMemberId, String teamName, String role, String status) {
        this.teamMemberId = teamMemberId;
        this.teamName = teamName;
        this.role = role;
        this.status = status;
    }

    public static TeamMemberResponse fromEntity(TeamMember entity) {
        return new TeamMemberResponse(
                entity.getTeamMemberId(),
                entity.getTeam().getTeamName(),
                entity.getRole().name(),
                entity.getStatus().name()
        );
    }

}
