package com.minsproject.league.constant.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchStatus {
    PENDING,

    ACCEPTED,

    FINISHED,

    REJECTED,

    CANCELED,

    NO_RESPONSE
}
