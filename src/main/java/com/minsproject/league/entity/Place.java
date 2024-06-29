package com.minsproject.league.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String town;

    private String dong;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private Integer zipcode;

    public Place(Long placeId) {
        this.placeId = placeId;
    }

    public Place(String city, String town, String dong, String detailAddress, Integer zipcode) {
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }
}
