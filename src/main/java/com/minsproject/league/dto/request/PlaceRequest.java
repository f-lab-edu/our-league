package com.minsproject.league.dto.request;

import com.minsproject.league.entity.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceRequest {

    private Long placeId;

    private String placeName;

    private String city;

    private String town;

    private String dong;

    private String detailAddress;

    private Integer zipcode;

    public Place toEntity() {
        return new Place(
                this.placeName,
                this.city,
                this.town,
                this.dong,
                this.detailAddress,
                this.zipcode
        );
    }
}
