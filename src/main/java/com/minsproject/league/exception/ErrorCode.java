package com.minsproject.league.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을 수 없어요."),

    SPORTS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 종목을 찾을 수 없어요.")

    ;
    private HttpStatus status;
    private String message;
}
