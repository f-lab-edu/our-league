package com.minsproject.league.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "팀 검색을 위한 DTO")
@ToString
public class TeamSearchDTO {

    @Schema(description = "페이지 크기", example = "10", defaultValue = "10")
    private Integer pageSize = 10;

    @Schema(description = "검색 시작 번호", example = "0", defaultValue = "0")
    private Long offsetId = 0L;

    @Schema(description = "종목 아이디", example = "1")
    private Long sportsId;

    @Schema(description = "사용자 위도 위치", example = "37.5665")
    private Double lat;

    @Schema(description = "사용자 경도 위치", example = "126.9780")
    private Double lon;

    @Schema(description = "검색 반경 (단위: km)", example = "5.0")
    private Double radius;

}
