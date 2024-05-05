package com.minsproject.league.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minsproject.league.constant.UserRole;
import com.minsproject.league.entity.Users;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Getter
public class UsersDTO implements UserDetails {

    private Long userId;

    private String email;

    private String name;

    private String password;

    private String mobilNumber;

    private String socialLoginType;

    private String socialLoginId;

    private UserRole role;

    private Long status;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    @Builder
    private UsersDTO(Long userId, String email, String password, String name, String mobilNumber, String socialLoginType, String socialLoginId, UserRole role, Long status, Timestamp createdAt, Timestamp modifiedAt) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobilNumber = mobilNumber;
        this.socialLoginType = socialLoginType;
        this.socialLoginId = socialLoginId;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UsersDTO fromEntity(Users entity) {
        return UsersDTO.builder()
                .userId(entity.getUserId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .mobilNumber(entity.getMobilNumber())
                .socialLoginType(entity.getSocialLoginType())
                .socialLoginId(entity.getSocialLoginId())
                .role(entity.getRole())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.name;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String toString() {
        return "UsersDTO{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mobilNumber='" + mobilNumber + '\'' +
                ", socialLoginType='" + socialLoginType + '\'' +
                ", socialLoginId='" + socialLoginId + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
