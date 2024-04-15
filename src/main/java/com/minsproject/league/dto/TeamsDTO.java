package com.minsproject.league.dto;

import com.minsproject.league.entity.Sports;
import com.minsproject.league.entity.Teams;
import com.minsproject.league.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamsDTO {

    private Long teamId;

    private Sports sports;

    private String teamName;

    private String description;

    private String fullAddress; //통합주소

    private String city; //시

    private String town; //구

    private String dong; //동

    private String detailAddress; //상세주소

    private Long status;

    private Users creator;

    private Users modifier;

    public static TeamsDTO fromEntity(Teams teams) {
        return new TeamsDTO(
                teams.getTeamId(),
                teams.getSports(),
                teams.getTeamName(),
                teams.getDescription(),
                teams.getFullAddress(),
                teams.getCity(),
                teams.getTown(),
                teams.getDong(),
                teams.getDetailAddress(),
                teams.getStatus(),
                teams.getCreator(),
                teams.getModifier()
        );
    }
}
