package com.minsproject.league.dto.response;

import com.minsproject.league.entity.Team;
import lombok.Getter;

@Getter
public class TeamMapResponse {

    private Long teamId;

    private double lat;

    private double lon;

    private String fullAddress;

    private TeamMapResponse(Long teamId, double lat, double lon, String fullAddress) {
        this.teamId = teamId;
        this.lat = lat;
        this.lon = lon;
        this.fullAddress = fullAddress;
    }

    public static TeamMapResponse fromEntity(Team entity) {
        return new TeamMapResponse(
            entity.getTeamId(),
            entity.getLat(),
            entity.getLon(),
            entity.getFullAddress()
        );
    }
}
