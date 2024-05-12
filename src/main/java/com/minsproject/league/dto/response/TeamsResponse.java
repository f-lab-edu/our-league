package com.minsproject.league.dto.response;

import com.minsproject.league.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamsResponse {

    private Long sportsId;

    private String sportsName;

    private String teamName;

    private String description;

    private String fullAddress;

    private String city;

    private String town;

    private String dong;

    private Long status;

    public static TeamsResponse fromEntity(Team entity) {
        return new TeamsResponse(
                entity.getSports().getSportsId(),
                entity.getSports().getName(),
                entity.getTeamName(),
                entity.getDescription(),
                entity.getFullAddress(),
                entity.getCity(),
                entity.getTown(),
                entity.getDong(),
                entity.getStatus()
        );
    }
}
