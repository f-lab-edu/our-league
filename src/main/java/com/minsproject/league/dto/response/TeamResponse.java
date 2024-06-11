package com.minsproject.league.dto.response;

import com.minsproject.league.constant.status.TeamStatus;
import com.minsproject.league.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class TeamResponse {

    private Long sportsId;

    private String sportsName;

    private String teamName;

    private String description;

    private String fullAddress;

    private String city;

    private String town;

    private String dong;

    private TeamStatus status;

    public static TeamResponse fromEntity(Team entity) {
        return new TeamResponse(
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamResponse that = (TeamResponse) o;
        return Objects.equals(sportsId, that.sportsId) && Objects.equals(sportsName, that.sportsName) && Objects.equals(teamName, that.teamName) && Objects.equals(description, that.description) && Objects.equals(fullAddress, that.fullAddress) && Objects.equals(city, that.city) && Objects.equals(town, that.town) && Objects.equals(dong, that.dong) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sportsId, sportsName, teamName, description, fullAddress, city, town, dong, status);
    }
}
