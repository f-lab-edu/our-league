package com.minsproject.league.entity;

import com.minsproject.league.constant.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String mobilNumber;

    private String socialLoginType;

    private String socialLoginId;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    private Long status;

    @Builder
    private User(String email, String name, String password, String mobilNumber, String socialLoginType, String socialLoginId) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobilNumber = mobilNumber;
        this.socialLoginType = socialLoginType;
        this.socialLoginId = socialLoginId;
    }

}
