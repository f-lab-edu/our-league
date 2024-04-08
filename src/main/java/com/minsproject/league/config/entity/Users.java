package com.minsproject.league.config.entity;

import jakarta.persistence.*;

@Entity
public class Users extends BaseEntity {

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

    private Long status;

}
