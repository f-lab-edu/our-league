package com.minsproject.league.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MatchSearchDTO {

    private Integer pageSize;

    private Long offsetId;

    private String status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public MatchSearchDTO(Integer pageSize, Long offsetId) {
        setPageSizeAndOffsetId(pageSize, offsetId);
    }

    public MatchSearchDTO(Integer pageSize, Long offsetId, String status, LocalDateTime startDate, LocalDateTime endDate) {
        setPageSizeAndOffsetId(pageSize, offsetId);
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void setPageSizeAndOffsetId(Integer pageSize, Long offsetId) {
        this.pageSize = pageSize == null ? 10 : pageSize;
        this.offsetId = offsetId == null ? 0 : offsetId;
    }
}