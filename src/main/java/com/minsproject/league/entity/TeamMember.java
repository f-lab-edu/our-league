package com.minsproject.league.entity;

import com.minsproject.league.constant.TeamMemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Entity
public class TeamMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @ManyToOne
    private Team teamId;

    @ManyToOne
    private User userId;

    @Getter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamMemberRole role;

    @Getter
    @Column(nullable = false)
    private Long status;

    private Timestamp statusChangedAt;
}
