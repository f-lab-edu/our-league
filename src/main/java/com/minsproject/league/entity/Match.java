package com.minsproject.league.entity;

import com.minsproject.league.constant.status.MatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Match extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_team_id")
    private Team inviter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_team_id")
    private Team invitee;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private LocalDateTime matchDay;

    @Column(nullable = false)
    private MatchStatus status;

    public Match(Team inviter, Team invitee, Place place, LocalDateTime matchDay, MatchStatus status) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.place = place;
        this.matchDay = matchDay;
        this.status = status;
    }
}
