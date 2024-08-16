package com.minsproject.league.dto.response;

import com.minsproject.league.entity.Match;
import com.minsproject.league.entity.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "매치 응답 DTO")
@NoArgsConstructor
public class MatchResponse {

    @Schema(description = "매치 ID")
    private Long matchId;

    @Schema(description = "초대자 팀 이름", example = "강남 배드민턴 클럽")
    private String inviterName;

    @Schema(description = "매칭 장소 주소", example = "서울시 강남구 신사동 111")
    private String placeAddress;

    @Schema(description = "매치 날짜", example = "2024-07-01T14:30:00")
    private LocalDateTime matchDay;

    @Schema(description = "매칭 상태", example = "PENDING, ACCEPTED, FINISHED, REJECTED, NO_RESPONSE")
    private String status;

    public MatchResponse(Long matchId, String inviterName, String placeAddress, LocalDateTime matchDay, String status) {
        this.matchId = matchId;
        this.inviterName = inviterName;
        this.placeAddress = placeAddress;
        this.matchDay = matchDay;
        this.status = status;
    }

    public static MatchResponse fromEntity(Match entity) {
        return new MatchResponse(
                entity.getMatchId(),
                entity.getInviter().getTeamName(),
                concatPlaceFullAddress(entity.getPlace()),
                entity.getMatchDay(),
                entity.getStatus().name()
        );
    }

    private static String concatPlaceFullAddress(Place place) {
        return place.getCity() + " "
                + place.getCity() + " "
                + place.getTown() + " "
                + place.getDong() + " "
                + place.getDetailAddress() + " ";
    }
}
