package com.minsproject.league.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@AllArgsConstructor
public class TeamCreateRequest {

    private Long sportsId;

    private String teamName;

    private String description;

    private String fullAddress;

    private String city;

    private String town;

    private String dong;

    private String detailAddress;

    private Long creatorId;
}
