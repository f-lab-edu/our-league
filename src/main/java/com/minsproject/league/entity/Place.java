package com.minsproject.league.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String dong;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private Integer zipcode;

    public Place(String placeName, String city, String town, String dong, String detailAddress, Integer zipcode) {
        this.placeName = placeName;
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }
}
