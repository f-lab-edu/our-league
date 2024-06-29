package com.minsproject.league.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TeamSearchDTO {

    private Integer pageSize = 10;

    private Long offsetId = 0L;

    @Setter
    private String city;

    @Setter
    private String town;

    @Setter
    private String dong;

    @Setter
    private Long sportsId;

    public TeamSearchDTO(Integer pageSize, Long offsetId) {
        this.pageSize = pageSize;
        this.offsetId = offsetId;
    }

    public TeamSearchDTO(Integer pageSize, Long offsetId, String city, String town, String dong, Long sportsId) {
        this.pageSize = pageSize;
        this.offsetId = offsetId;
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.sportsId = sportsId;
    }

}
