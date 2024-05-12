package com.minsproject.league.entity;

import jakarta.persistence.*;

@Entity
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @ManyToOne
    private Sports sportsId;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String town;

    private String dong;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private Integer zipcode;

}
