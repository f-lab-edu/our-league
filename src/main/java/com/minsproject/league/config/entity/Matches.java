package com.minsproject.league.config.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Matches extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVITER_TEAM_ID")
    private Team inviterTeamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVITEE_TEAM_ID")
    private Team inviteeTeamId;

    private String matchLocation;

    @ManyToOne
    private Places placeId;

    private Timestamp matchDay;

    @Column(nullable = false)
    private Long status;

}
