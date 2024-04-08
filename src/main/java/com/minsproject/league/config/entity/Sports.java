package com.minsproject.league.config.entity;

import jakarta.persistence.*;

@Entity
public class Sports extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sportsId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long status;

}
