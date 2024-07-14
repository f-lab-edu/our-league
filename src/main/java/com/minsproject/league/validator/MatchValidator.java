package com.minsproject.league.validator;

import com.minsproject.league.dto.PlaceDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MatchValidator {
    public boolean isMatchDayBeforeNow(LocalDateTime matchDay) {
        return matchDay.isBefore(LocalDateTime.now());
    }

    public boolean isPlaceNotNull(PlaceDTO place) {
        return place.getCity() != null
                || place.getTown() != null
                || place.getDong() != null
                || place.getDetailAddress() != null;
    }
}
