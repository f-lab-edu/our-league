package com.minsproject.league.dto;

import lombok.Getter;

@Getter
public class TeamSearchDTO {

    private Integer pageSize;

    private Long offsetId;

    private TeamSearchDTO(Integer pageSize, Long offsetId) {
        this.pageSize = pageSize;
        this.offsetId = offsetId;
    }

    public static TeamSearchDTO of(Integer pageSize, Long offsetId) {
        pageSize = pageSize == null ? 10 : pageSize;

        return new TeamSearchDTO(pageSize, offsetId);
    }
}
