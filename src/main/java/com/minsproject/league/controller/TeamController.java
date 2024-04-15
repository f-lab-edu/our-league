package com.minsproject.league.controller;

import com.minsproject.league.controller.request.TeamCreateRequest;
import com.minsproject.league.controller.request.TeamModifyRequest;
import com.minsproject.league.controller.response.Response;
import com.minsproject.league.controller.response.TeamsResponse;
import com.minsproject.league.dto.TeamCreateDTO;
import com.minsproject.league.dto.TeamsDTO;
import com.minsproject.league.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping()
    public Response<Page<TeamsResponse>> teamList(Pageable pageable) {
        return Response.success(teamService.getTeamList(pageable).map(TeamsResponse::fromDTO));
    }

    @PostMapping()
    public Response<Void> create(@RequestBody TeamCreateRequest req) {
        teamService.create(TeamCreateDTO.toDto(req));
        return Response.success();
    }

//    @PutMapping("/{teamId}")
//    public Response<TeamsResponse> modify(@PathVariable Long teamId, @RequestBody TeamModifyRequest req) {
//        TeamsDTO team = teamService.modify()
//        return Response.success();
//    }

}
