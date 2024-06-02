package com.minsproject.league.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을 수 없어요."),

    SPORTS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 종목을 찾을 수 없어요."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없어요."),

    TEAM_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "팀 멤버를 찾을 수 없어요."),

    MODIFICATION_NOT_ALLOWED(HttpStatus.UNAUTHORIZED, "수정할 수 없습니다."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "회원이나 비밀번호를 확인해주세요."),

    DELETING_NOT_ALLOWED(HttpStatus.NOT_FOUND, "삭제할 수 없습니다."),

    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "이미 가입한 이메일입니다."),

    ;

    private HttpStatus status;
    private String message;
}
