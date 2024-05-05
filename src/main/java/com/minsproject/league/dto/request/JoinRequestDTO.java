package com.minsproject.league.dto.request;

import lombok.Getter;

@Getter
public class JoinRequestDTO {

    private String email;

    private String name;

    private String password;

    private String mobilNumber;

    private String socialLoginType;

    private String socialLoginId;

    public JoinRequestDTO(String email, String name, String password, String mobilNumber, String socialLoginType, String socialLoginId) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobilNumber = mobilNumber;
        this.socialLoginType = socialLoginType;
        this.socialLoginId = socialLoginId;
    }
}
