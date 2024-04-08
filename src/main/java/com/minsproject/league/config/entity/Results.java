package com.minsproject.league.config.entity;

import jakarta.persistence.*;

@Entity
public class Results extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @Column(nullable = false)
    private Long matchId;

    @Column(nullable = false)
    private Long teamId;

    private Long result; //3: 승, 1: 무, 0: 패
}
