package com.minsproject.league.dto.request;

import com.minsproject.league.constant.status.MatchStatus;
import com.minsproject.league.entity.Match;
import com.minsproject.league.entity.Place;
import com.minsproject.league.entity.Team;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MatchRequest {

    @NotNull
    private Long inviterTeamId;

    @NotNull(message = "매칭 상대를 선택해주세요.")
    private Long inviteeTeamId;

    @NotNull(message = "매칭 장소를 선택해주세요.")
    private PlaceRequest place;

    @NotNull(message = "매칭 날짜를 선택해주세요.")
    private LocalDateTime matchDay;

    private MatchStatus status;

    public MatchRequest(Long inviterTeamId, Long inviteeTeamId, PlaceRequest place, LocalDateTime matchDay, MatchStatus status) {
        this.inviterTeamId = inviterTeamId;
        this.inviteeTeamId = inviteeTeamId;
        this.place = place;
        this.matchDay = matchDay;
        this.status = status;
    }

    public Match toEntity(Team inviter, Team invitee, Place place) {
        return new Match(
                inviter,
                invitee,
                place,
                this.matchDay,
                this.status
        );
    }
}
