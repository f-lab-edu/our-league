package com.minsproject.league.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TeamSearchDTO {

    private Integer pageSize;

    private Long offsetId;

    @Setter
    private String city;

    @Setter
    private String town;

    @Setter
    private String dong;

    @Setter
    private Long sportsId;

    public TeamSearchDTO(Integer pageSize, Long offsetId) {
        setPageSizeAndOffsetId(pageSize, offsetId);
    }

    public TeamSearchDTO(Integer pageSize, Long offsetId, String city, String town, String dong, Long sportsId) {
        setPageSizeAndOffsetId(pageSize, offsetId);
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.sportsId = sportsId;
    }

    private void setPageSizeAndOffsetId(Integer pageSize, Long offsetId) {
        this.pageSize = pageSize == null ? 10 : pageSize;
        this.offsetId = offsetId == null ? 0 : offsetId;
    }
}
