package com.minsproject.league.controller;

import com.minsproject.league.controller.request.TeamCreateRequest;
import com.minsproject.league.controller.response.TeamsResponse;
import com.minsproject.league.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "팀", description = "팀 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping()
    @Operation(summary = "팀 목록 조회")
    public Page<TeamsResponse> teamList(Pageable pageable) {
        return teamService.getTeamList(pageable).map(TeamsResponse::fromEntity);
    }

    @PostMapping()
    @Operation(summary = "팀 만들기")
    @ApiResponse(responseCode = "200", description = "팀 만들기 성공, 팀 아이디를 반환한다")
    public long create(@RequestBody TeamCreateRequest req) {
        return teamService.create(req);
    }
}
