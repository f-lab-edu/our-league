package com.minsproject.league.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MatchSearchDTO {

    private Integer pageSize = 10;

    private Long offsetId = 0L;

    private Long teamId;

    private String status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public MatchSearchDTO(Integer pageSize, Long offsetId, Long teamId) {
        this.pageSize = pageSize;
        this.offsetId = offsetId;
        this.teamId = teamId;
    }

    public MatchSearchDTO(Integer pageSize, Long offsetId, String status, LocalDateTime startDate, LocalDateTime endDate) {
        this.pageSize = pageSize;
        this.offsetId = offsetId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}