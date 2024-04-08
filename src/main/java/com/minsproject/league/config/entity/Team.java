package com.minsproject.league.config.entity;

import jakarta.persistence.*;

@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sports sportsId;

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

    @ManyToOne
    private Users created_by;

    @ManyToOne
    private Users modified_by;

}
