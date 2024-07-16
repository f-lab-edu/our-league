package com.minsproject.league.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // NOT_FOUND
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을 수 없어요."),

    SPORTS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 종목을 찾을 수 없어요."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없어요."),

    TEAM_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "팀 멤버를 찾을 수 없어요."),

    DELETING_NOT_ALLOWED(HttpStatus.NOT_FOUND, "삭제할 수 없습니다."),

    // UNAUTHORIZED
    MODIFICATION_NOT_ALLOWED(HttpStatus.UNAUTHORIZED, "수정할 수 없습니다."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "회원이나 비밀번호를 확인해주세요."),

    // BAD_REQUEST
    DUPLICATED_USER_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입한 이메일입니다."),

    TEAM_NOT_ACCEPTING_MEMBER(HttpStatus.BAD_REQUEST, "지금은 팀에 가입할 수 없습니다."),

    ALREADY_IN_TEAM(HttpStatus.BAD_REQUEST, "이미 가입한 팀입니다."),

    MATCH_INVITE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "매칭 신청 권한이 없습니다."),

    TEAM_NOT_ACCEPTING_MATCHES(HttpStatus.BAD_REQUEST, "해당 팀은 현재 매칭을 받고 있지 않습니다."),

    ADDRESS_NOT_SUITABLE_FOR_MATCH(HttpStatus.BAD_REQUEST, "매칭을 진행할 수 없는 주소입니다."),

    INVALID_MATCH_DAY(HttpStatus.BAD_REQUEST, "매칭 날짜는 오늘 이후여야 합니다."),


    INVALID_MATCH_PLACE(HttpStatus.BAD_REQUEST, "매칭 장소가 없습니다."),

    ;

    private HttpStatus status;
    private String message;
}
