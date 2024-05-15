package com.minsproject.league.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response {

    private String resultCode;

    public static Response error(String errorCode) {
        return new Response(errorCode);
    }
}
