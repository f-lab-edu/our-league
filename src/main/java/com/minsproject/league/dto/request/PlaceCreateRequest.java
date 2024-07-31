package com.minsproject.league.dto.request;

import com.minsproject.league.entity.Place;
import lombok.Getter;

@Getter
public class PlaceCreateRequest {

    private String placeName;

    private String city;

    private String town;

    private String dong;

    private String detailAddress;

    private Integer zipcode;

    public static Place toEntity(PlaceCreateRequest request) {
        return new Place(
                request.getPlaceName(),
                request.getCity(),
                request.getTown(),
                request.getDong(),
                request.getDetailAddress(),
                request.getZipcode()
        );
    }

}
