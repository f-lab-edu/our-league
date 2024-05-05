package com.minsproject.league.controller;

import com.minsproject.league.dto.request.JoinRequestDTO;
import com.minsproject.league.dto.request.LoginRequestDTO;
import com.minsproject.league.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO req) {
        return userService.login(req);
    }

    @PostMapping("/join")
    public Long join(@RequestBody JoinRequestDTO req) {
        return userService.join(req);
    }
}
