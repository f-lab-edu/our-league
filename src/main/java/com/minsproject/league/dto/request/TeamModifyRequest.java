package com.minsproject.league.dto.request;

import lombok.Getter;

@Getter
public class TeamModifyRequest {

    private Long teamId;

    private Long sportsId;

    private String teamName;

    private String description;

    private String dong;

    private String detailAddress;

    private Long status;

    private Long modifierId;
}
