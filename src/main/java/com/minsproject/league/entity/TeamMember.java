package com.minsproject.league.entity;

import com.minsproject.league.constant.TeamMemberRole;
import com.minsproject.league.constant.status.TeamMemberStatus;
import com.minsproject.league.dto.TeamMemberDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@SQLRestriction("deleted = false")
public class TeamMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamMemberRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamMemberStatus status;

    private Timestamp statusChangedAt;

    @Column(name = "deleted")
    private boolean isDeleted;

    @Builder
    private TeamMember(Team team, User user, TeamMemberRole role, TeamMemberStatus status) {
        this.team = team;
        this.user = user;
        this.role = role;
        this.status = status;
    }

    public void modify(TeamMemberDTO dto) {
        this.role = TeamMemberRole.valueOf(dto.getRole().toUpperCase());
        this.status = TeamMemberStatus.valueOf(dto.getStatus().toUpperCase());
        this.statusChangedAt = new Timestamp(System.currentTimeMillis());
    }

    public void delete() {
        this.isDeleted = true;
    }
}
