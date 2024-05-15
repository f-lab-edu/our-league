package com.minsproject.league.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minsproject.league.dto.UserDTO;
import com.minsproject.league.dto.request.JoinRequestDTO;
import com.minsproject.league.dto.request.LoginRequestDTO;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.UsersRepository;
import com.minsproject.league.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class UserService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public UserDTO loadUserByUserEmail(String email) {
        return usersRepository.findByEmail(email).map(UserDTO::fromEntity).orElseThrow(() -> new LeagueCustomException(ErrorCode.USER_NOT_FOUND));
    }

    public String login(LoginRequestDTO req) {
        UserDTO user = loadUserByUserEmail(req.getEmail());

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new LeagueCustomException(ErrorCode.INVALID_PASSWORD);
        }

        return JwtTokenUtils.generateToken(req.getEmail(), secretKey, expiredTimeMs);
    }

    @Transactional
    public Long join(JoinRequestDTO req) {
        usersRepository.findByEmail(req.getEmail()).ifPresent(it -> {
            throw new LeagueCustomException(ErrorCode.DUPLICATED_USER_EMAIL);
        });
        req.setPasswordEncoded(encoder.encode(req.getPassword()));
        return usersRepository.save(JoinRequestDTO.toEntity(req)).getUserId();
    }

}
