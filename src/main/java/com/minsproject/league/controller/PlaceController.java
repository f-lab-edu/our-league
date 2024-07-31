package com.minsproject.league.controller;

import com.minsproject.league.dto.request.PlaceCreateRequest;
import com.minsproject.league.dto.response.PlaceResponse;
import com.minsproject.league.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "매칭 장소", description = "매칭 장소 API ENDPOINTS")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    @Operation(summary = "등록된 매칭 장소 목록 조회")
    @ApiResponse(responseCode = "200", description = "매칭 신청에서 사용 가능한 매칭 장소 목록을 반환한다")
    public List<PlaceResponse> placeList() {
        return placeService.getPlaceList();
    }

    @PostMapping
    @Operation(summary = "매칭 장소 등록")
    @ApiResponse(responseCode = "200", description = "매칭 신청에서 사용 가능한 매칭 장소 목록을 반환한다")
    public Long create(PlaceCreateRequest request) {
        return placeService.create(request);
    }
}
