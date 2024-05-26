package com.minsproject.league.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Sports extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sportsId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long status;

    @Builder
    private Sports(Long sportsId, String name) {
        this.sportsId = sportsId;
        this.name = name;
    }

}
