package com.minsproject.league.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamModifyRequest {

    private Long sportsId;

    private String teamName;

    private String description;

    private String dong;

    private String detailAddress;

    private Long status;

    private Long modifierId;
}
