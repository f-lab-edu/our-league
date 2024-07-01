package com.minsproject.league.entity;

import com.minsproject.league.constant.status.MatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "matches")
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

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    public Match(Team inviter, Team invitee, Place place, LocalDateTime matchDay, MatchStatus status) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.place = place;
        this.matchDay = matchDay;
        this.status = status;
    }

    public boolean isParticipant(Team team) {
        return isInviter(team) || isInvitee(team);
    }

    public boolean isInviter(Team team) {
        return this.inviter.equals(team);
    }

    public boolean isInvitee(Team team) {
        return this.invitee.equals(team);
    }

    public boolean isStatusValidForResult() {
        return this.status == MatchStatus.ACCEPTED;
    }

    public boolean isPastMatchDay() {
        return this.matchDay.isAfter(LocalDateTime.now());
    }
}
