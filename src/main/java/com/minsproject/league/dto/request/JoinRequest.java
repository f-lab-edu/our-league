package com.minsproject.league.dto.request;

import com.minsproject.league.entity.User;
import lombok.Getter;

@Getter
public class JoinRequest {

    private String email;

    private String name;

    private String password;

    private String mobilNumber;

    private String socialLoginType;

    private String socialLoginId;

    public JoinRequest(String email, String name, String password, String mobilNumber, String socialLoginType, String socialLoginId) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobilNumber = mobilNumber;
        this.socialLoginType = socialLoginType;
        this.socialLoginId = socialLoginId;
    }

    public static User toEntity(JoinRequest req) {
        return User.builder()
                .email(req.getEmail())
                .name(req.getName())
                .password(req.getPassword())
                .mobilNumber(req.getMobilNumber())
                .socialLoginType(req.getSocialLoginType())
                .socialLoginId(req.getSocialLoginId())
                .build();
    }

    public void setPasswordEncoded(String encodedPassword) {
        this.password = encodedPassword;
    }

}
