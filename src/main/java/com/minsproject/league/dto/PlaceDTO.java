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

    public static Place toEntity(PlaceDTO dto) {
        if (dto.getPlaceId() == null) {
            return new Place(
                    dto.getCity(),
                    dto.getTown(),
                    dto.getDong(),
                    dto.getDetailAddress(),
                    dto.getZipcode()
            );
        }

        return new Place(
                dto.getPlaceId()
        );
    }
}
