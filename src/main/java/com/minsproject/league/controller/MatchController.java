package com.minsproject.league.controller;

import com.minsproject.league.dto.MatchDTO;
import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.dto.UserDTO;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "매칭", description = "매칭 API ENDPOINTS")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/match")
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    @Operation(summary = "매칭이 가능한 팀 목록 조회")
    @ApiResponse(responseCode = "200", description = "동일한 주소, 운동 종목을 가진 매칭 가능 팀 목록을 반환한다")
    public List<TeamResponse> teamListForMatch(TeamSearchDTO teamSearchDTO) {
        return matchService.getTeamList(teamSearchDTO);
    }

    @PostMapping
    @Operation(summary = "매칭 신청")
    @ApiResponse(responseCode = "200", description = "신청된 매칭 아이디를 반환한다")
    public Long createMatch(MatchDTO matchDTO, @AuthenticationPrincipal UserDTO userDTO) {
        return matchService.createMatch(matchDTO, userDTO);
    }

}
