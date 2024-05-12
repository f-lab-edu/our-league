package com.minsproject.league.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class TeamMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @ManyToOne
    private Team teamId;

    @ManyToOne
    private User userId;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Long status;

    private Timestamp status_changed_at;
}
