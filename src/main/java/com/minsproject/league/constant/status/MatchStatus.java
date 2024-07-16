package com.minsproject.league.constant.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchStatus {
    WAITING,

    ACCEPTED,

    FINISHED,

    REJECTED,

    NO_RESPONSE
}
