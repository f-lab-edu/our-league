package com.minsproject.league.dto;

import com.minsproject.league.entity.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceDTO {

    private Long placeId;

    private String city;

    private String town;

    private String dong;

    private String detailAddress;

    private Integer zipcode;

    public Place toEntity() {
        return new Place(
                this.city,
                this.city,
                this.dong,
                this.detailAddress,
                this.zipcode
        );
    }

    public boolean isNewPlace() {
        return this.placeId == null;
    }
}
