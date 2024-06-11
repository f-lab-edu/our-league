package com.minsproject.league.dto.request;

import com.minsproject.league.constant.status.TeamStatus;
import com.minsproject.league.entity.Sports;
import com.minsproject.league.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TeamCreateRequest {

    @Schema(description = "종목 코드", nullable = false)
    private Long sportsId;

    @Schema(description = "팀 이름", nullable = false)
    private String teamName;

    @Schema(description = "팀 소개글", nullable = true)
    private String description;

    @Schema(description = "팀 전체주소", nullable = false)
    private String fullAddress;

    @Schema(description = "팀이 활동하는 시", nullable = false)
    private String city;

    @Schema(description = "팀이 활동하는 구", nullable = false)
    private String town;

    @Schema(description = "팀이 활동하는 동", nullable = false)
    private String dong;

    @Schema(description = "상세 주소", nullable = false)
    private String detailAddress;

    @Schema(description = "팀 등록자 이름", nullable = false)
    private String creator;

    @Builder
    private TeamCreateRequest(Long sportsId, String teamName, String description, String fullAddress, String city, String town, String dong, String detailAddress, String creator) {
        this.sportsId = sportsId;
        this.teamName = teamName;
        this.description = description;
        this.fullAddress = fullAddress;
        this.city = city;
        this.town = town;
        this.dong = dong;
        this.detailAddress = detailAddress;
        this.creator = creator;
    }

    public static Team toEntity(TeamCreateRequest dto, Sports sports) {
        return Team.builder()
                .sports(sports)
                .teamName(dto.getTeamName())
                .description(dto.getDescription())
                .fullAddress(dto.getFullAddress())
                .city(dto.getCity())
                .town(dto.getTown())
                .dong(dto.getDong())
                .detailAddress(dto.getDetailAddress())
                .status(TeamStatus.ACCEPTING)
                .build();
    }
}
