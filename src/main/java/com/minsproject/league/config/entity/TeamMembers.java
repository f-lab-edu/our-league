package com.minsproject.league.config.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class TeamMembers extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @ManyToOne
    private Team teamId;

    @ManyToOne
    private Users userId;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Long status;

    private Timestamp status_changed_at;
}
