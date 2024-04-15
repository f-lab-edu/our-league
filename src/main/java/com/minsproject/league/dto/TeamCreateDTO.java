package com.minsproject.league.dto;

import com.minsproject.league.controller.request.TeamCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamCreateDTO {

    private Long sportsId;

    private String teamName;

    private String description;

    private String fullAddress;

    private String city;

    private String town;

    private String dong;

    private String detailAddress;

    private Long creatorId;

    public static TeamCreateDTO toDto(TeamCreateRequest req) {
        return new TeamCreateDTO(
                req.getSportsId(),
                req.getTeamName(),
                req.getDescription(),
                req.getFullAddress(),
                req.getCity(),
                req.getTown(),
                req.getDong(),
                req.getDetailAddress(),
                req.getCreatorId()
        );
    }
}
