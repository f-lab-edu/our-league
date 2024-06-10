package com.minsproject.league.service;

import com.minsproject.league.entity.Sports;
import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import com.minsproject.league.repository.SportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SportsService {

    private final SportsRepository sportsRepository;

    public Sports getSportsById(Long sportsId) {
        return sportsRepository.findById(sportsId).orElseThrow(() -> new LeagueCustomException(ErrorCode.SPORTS_NOT_FOUND));
    }

}
