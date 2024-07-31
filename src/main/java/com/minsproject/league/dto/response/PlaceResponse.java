package com.minsproject.league.dto.response;

import com.minsproject.league.entity.Place;

public class PlaceResponse {

    private Long placeId;

    private String placeName;

    private String city;

    private String town;

    private String dong;

    private String detailAddress;

    private Integer zipcode;

    private PlaceResponse(Long placeId, String placeName, String city, String town, String dong, String detailAddress, Integer zipcode) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }

    public static PlaceResponse fromEntity(Place entity) {
        return new PlaceResponse(
                entity.getPlaceId(),
                entity.getPlaceName(),
                entity.getCity(),
                entity.getTown(),
                entity.getDong(),
                entity.getDetailAddress(),
                entity.getZipcode()
        );
    }
}
