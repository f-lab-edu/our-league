package com.minsproject.league.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {

    private String resultCode;

    private T result;

    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null);
    }
}
