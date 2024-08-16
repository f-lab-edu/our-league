package com.minsproject.league.service;

import com.minsproject.league.dto.request.PlaceCreateRequest;
import com.minsproject.league.dto.request.PlaceRequest;
import com.minsproject.league.dto.response.PlaceResponse;
import com.minsproject.league.entity.Place;
import com.minsproject.league.entity.PlaceRepository;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Place getPlace(PlaceRequest place) {
        return placeRepository.findById(place.getPlaceId()).orElseThrow(() -> new LeagueCustomException(ErrorCode.INVALID_MATCH_PLACE));

    }

    public List<PlaceResponse> getPlaceList() {
        return placeRepository.findAll(Sort.by(Sort.Direction.DESC, "placeName")).stream().map(PlaceResponse::fromEntity).toList();
    }

    public Long create(PlaceCreateRequest request) {
        return placeRepository.save(PlaceCreateRequest.toEntity(request)).getPlaceId();
    }
}
