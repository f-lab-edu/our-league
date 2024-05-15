package com.minsproject.league.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamSearchDTO {

    private Integer pageSize = 10;

    private Long offsetId;
}
