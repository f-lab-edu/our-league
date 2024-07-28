package com.minsproject.league.controller;

import com.minsproject.league.dto.request.TeamMemberRequest;
import com.minsproject.league.dto.request.UserRequest;
import com.minsproject.league.dto.response.TeamMemberResponse;
import com.minsproject.league.service.TeamMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "팀 멤버", description = "팀 멤버 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teamMember")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @PostMapping("/{teamId}")
    @Operation(summary = "팀 멤버 등록")
    @ApiResponse(responseCode = "200", description = "팀 멤버 등록에 성공하면 등록된 팀 멤버의 ID를 반환한다")
    public Long create(@PathVariable Long teamId, Authentication authentication) {
        UserRequest principal = (UserRequest) authentication.getPrincipal();
        return teamMemberService.create(teamId, principal);
    }

    @PutMapping
    @Operation(summary = "팀 멤버 수정")
    @ApiResponse(responseCode = "200", description = "팀 멤버 수정에 성공하면 수정된 팀 멤버를 반환한다")
    public TeamMemberResponse modify(@RequestBody TeamMemberRequest teamMemberRequest, Authentication authentication) {
        UserRequest principal = (UserRequest) authentication.getPrincipal();
        return teamMemberService.modify(teamMemberRequest, principal);
    }

    @DeleteMapping("/{teamMemberId}")
    public void delete(@PathVariable Long teamMemberId, @AuthenticationPrincipal UserRequest user) {
        teamMemberService.delete(teamMemberId, user);
    }
}
