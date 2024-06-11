package com.minsproject.league.dto.request;

import com.minsproject.league.constant.status.TeamStatus;
import lombok.Getter;

@Getter
public class TeamModifyRequest {

    private Long sportsId;

    private String teamName;

    private String description;

    private String dong;

    private String detailAddress;

    private TeamStatus status;

    private String modifier;

}
