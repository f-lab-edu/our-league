package com.minsproject.league.entity;

import com.minsproject.league.constant.status.TeamStatus;
import com.minsproject.league.dto.request.TeamModifyRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@SQLRestriction("deleted = false")
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_id")
    private Sports sports;

    @Column(unique = true)
    private String teamName;

    private String description;

    @Column(nullable = false)
    private String fullAddress; //통합주소

    @Column(nullable = false)
    private String city; //시

    @Column(nullable = false)
    private String town; //구

    private String dong; //동

    @Column(nullable = false)
    private String detailAddress; //상세주소

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    private String creator;

    private String modifier;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<TeamMember> teamMembers;

    @Column(name = "deleted")
    private boolean isDeleted;

    @Builder
    private Team(Long teamId, Sports sports, String teamName, String description, String fullAddress, String city, String town, String dong, String detailAddress, TeamStatus status, String creator) {
        this.teamId = teamId;
        this.sports = sports;
        this.teamName = teamName;
        this.description = description;
        this.fullAddress = fullAddress;
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.detailAddress = detailAddress;
        this.status = status;
        this.creator = creator;
    }

    public void modifyTeam(TeamModifyRequest dto, Sports sports, String username) {
        this.teamName = dto.getTeamName();
        this.description = dto.getDescription();
        this.sports = sports;
        this.dong = dto.getDong();
        this.detailAddress = dto.getDetailAddress();
        this.status = dto.getStatus();
        this.modifier = username;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
