package com.minsproject.league.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
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
