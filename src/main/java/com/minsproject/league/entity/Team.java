package com.minsproject.league.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sports sports;

    @Column(unique = true)
    private String teamName;

    private String description;

    @Column(nullable = false)
    private String fullAddress; //통합주소

    @Column(nullable = false)
    private String city; //시

    @Column(nullable = false)
    private String town; //구

    private String dong; //동

    @Column(nullable = false)
    private String detailAddress; //상세주소

    @Column(nullable = false)
    private Long status;

    private String creator;

    private String modifier;

    @Builder
    private Team(Long teamId, Sports sports, String teamName, String description, String fullAddress, String city, String town, String dong, String detailAddress, Long status) {
        this.teamId = teamId;
        this.sports = sports;
        this.teamName = teamName;
        this.description = description;
        this.fullAddress = fullAddress;
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.detailAddress = detailAddress;
        this.status = status;
    }

}
