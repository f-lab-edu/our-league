package com.minsproject.league.controller.response;

import com.minsproject.league.dto.TeamsDTO;
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

    public static TeamsResponse fromDTO(TeamsDTO dto) {
        return new TeamsResponse(
                dto.getSports().getSportsId(),
                dto.getSports().getName(),
                dto.getTeamName(),
                dto.getDescription(),
                dto.getFullAddress(),
                dto.getCity(),
                dto.getTown(),
                dto.getDong(),
                dto.getStatus()
        );
    }
}
