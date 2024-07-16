package com.minsproject.league.controller;

import com.minsproject.league.dto.*;
import com.minsproject.league.dto.response.MatchResponse;
import com.minsproject.league.dto.response.TeamMapResponse;
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
    public List<TeamMapResponse> teamListForMatch(TeamSearchDTO teamSearchDTO) {
        return matchService.getTeamList(teamSearchDTO);
    }

    @PostMapping
    @Operation(summary = "매칭 신청")
    @ApiResponse(responseCode = "200", description = "신청된 매칭 아이디를 반환한다")
    public Long createMatch(MatchDTO matchDTO, @AuthenticationPrincipal UserDTO userDTO) {
        return matchService.createMatch(matchDTO, userDTO);
    }

    @GetMapping("/received/{teamId}")
    @Operation(summary = "받은 매칭 목록")
    @ApiResponse(responseCode = "200", description = "받은 매칭 목록을 반환한다")
    public List<MatchResponse> receivedMatchList(
            @PathVariable Long teamId,
            @ModelAttribute MatchSearchDTO matchSearchDTO) {
        return matchService.getReceivedMatchList(teamId, matchSearchDTO);
    }

    @GetMapping("/{matchId}/detail")
    @Operation(summary = "매칭 상세보기")
    @ApiResponse(responseCode = "200")
    public MatchResponse matchDetail(@PathVariable Long matchId) {
        return matchService.getMatchDetail(matchId);
    }

    @GetMapping("/{matchId}/accept")
    @Operation(summary = "매칭 수락하기")
    @ApiResponse(responseCode = "200")
    public Long acceptMatch(@PathVariable Long matchId) {
        return matchService.acceptMatch(matchId);
    }

    @GetMapping("/{matchId}/reject")
    @Operation(summary = "매칭 거절하기")
    @ApiResponse(responseCode = "200")
    public Long rejectMatch(@PathVariable Long matchId, @AuthenticationPrincipal UserDTO userDTO) {
        return matchService.rejectMatch(matchId, userDTO.getUserId());
    }

    @GetMapping("/{matchId}/cancel")
    @Operation(summary = "매칭 취소하기")
    @ApiResponse(responseCode = "200")
    public Long cancelMatch(@PathVariable Long matchId, @AuthenticationPrincipal UserDTO userDTO) {
        return matchService.cancelMatch(matchId, userDTO.getUserId());
    }

    @PostMapping("/{matchId}/result")
    @Operation(summary = "매칭 결과 입력하기")
    @ApiResponse(responseCode = "200")
    public Long result(@PathVariable Long matchId, @RequestBody Long teamId, @RequestBody String result, @AuthenticationPrincipal UserDTO userDTO) {
        return matchService.addResult(matchId, teamId, result, userDTO.getUserId());
    }
}
