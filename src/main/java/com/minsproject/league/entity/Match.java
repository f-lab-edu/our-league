package com.minsproject.league.entity;

import com.minsproject.league.constant.status.MatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class Match extends BaseEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_team_id")
    private Team inviterTeamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_team_id")
    private Team inviteeTeamId;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private LocalDateTime matchDay;

    @Column(nullable = false)
    private MatchStatus status;

    public Match(Team inviterTeamId, Team inviteeTeamId, Place place, LocalDateTime matchDay, MatchStatus status) {
        this.inviterTeamId = inviterTeamId;
        this.inviteeTeamId = inviteeTeamId;
        this.place = place;
        this.matchDay = matchDay;
        this.status = status;
    }
}
