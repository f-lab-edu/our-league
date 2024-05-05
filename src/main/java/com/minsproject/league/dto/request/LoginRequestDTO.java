package com.minsproject.league.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDTO {

    private String email;

    private String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
