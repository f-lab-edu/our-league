package com.minsproject.league.entity;

import com.minsproject.league.dto.TeamCreateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Teams extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Long status;

    @ManyToOne
    private Users creator;

    @ManyToOne
    private Users modifier;

    public Teams(Sports sports, String teamName, String description, String fullAddress, String city, String town, String dong, String detailAddress) {
        this.sports = sports;
        this.teamName = teamName;
        this.description = description;
        this.fullAddress = fullAddress;
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.detailAddress = detailAddress;
    }

    public static Teams fromDto(TeamCreateDTO dto, Sports sports) {
        return new Teams(
                sports,
                dto.getTeamName(),
                dto.getDescription(),
                dto.getFullAddress(),
                dto.getCity(),
                dto.getTown(),
                dto.getDong(),
                dto.getDetailAddress()
        );
    }
}
