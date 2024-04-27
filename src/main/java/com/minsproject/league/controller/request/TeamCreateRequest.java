package com.minsproject.league.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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

    @Schema(description = "팀 등록자 id", nullable = false)
    private Long creatorId;
}
