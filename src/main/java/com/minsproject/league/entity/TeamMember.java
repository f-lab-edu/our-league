package com.minsproject.league.entity;

import com.minsproject.league.constant.TeamMemberRole;
import com.minsproject.league.constant.status.TeamMemberStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Entity
public class TeamMember extends BaseEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamMemberRole role;

    @Getter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamMemberStatus status;

    private Timestamp statusChangedAt;

    @Builder
    private TeamMember(Team team, User user, TeamMemberRole role, TeamMemberStatus status) {
        this.team = team;
        this.user = user;
        this.role = role;
        this.status = status;
    }
}
