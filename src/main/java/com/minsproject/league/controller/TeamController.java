package com.minsproject.league.controller;

import com.minsproject.league.dto.TeamSearchDTO;
import com.minsproject.league.dto.UserDTO;
import com.minsproject.league.dto.request.TeamCreateRequest;
import com.minsproject.league.dto.request.TeamModifyRequest;
import com.minsproject.league.dto.response.TeamResponse;
import com.minsproject.league.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "팀", description = "팀 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    @Operation(summary = "팀 목록 조회")
    @ApiResponse(responseCode = "200", description = "검색 조건에 부합하는 팀 목록을 반환한다")
    public List<TeamResponse> teamList(TeamSearchDTO searchDTO) {
        return teamService.getTeamList(searchDTO);
    }

    @PostMapping
    @Operation(summary = "팀 등록")
    @ApiResponse(responseCode = "200", description = "팀 등록에 성공하면 등록된 ID를 반환한다")
    public Long create(@RequestBody TeamCreateRequest request) {
        return teamService.create(request);
    }

    @PutMapping("/{teamId}")
    @Operation(summary = "팀 수정")
    @ApiResponse(responseCode = "200", description = "팀 수정에 성공하면 수정된 팀 정보를 반환한다")
    public TeamResponse modify(@PathVariable Long teamId, @RequestBody TeamModifyRequest request, @AuthenticationPrincipal UserDTO userDTO) {
        return teamService.modify(teamId, request, userDTO);
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "팀 삭제")
    @ApiResponse(responseCode = "200")
    public void deleteTeam(@PathVariable Long teamId, @AuthenticationPrincipal UserDTO userDTO) {
        teamService.delete(teamId, userDTO);
    }
}
