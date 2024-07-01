package com.minsproject.league.entity;

import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Result extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    private String result; // W, D, L

    private Integer points; //3: 승, 1: 무, 0: 패

    private Result(Match match, Team team, String result) {
        this.match = match;
        this.team = team;
        this.result = result;
        this.points = calculatePoints(result);
    }

    public static Result of(Match match, Team team, String result) {
        return new Result(match, team, result);
    }

    private Integer calculatePoints(String result) {
        return switch (result) {
            case "W" -> 3;
            case "D" -> 1;
            case "L" -> 0;
            default -> throw new LeagueCustomException(ErrorCode.INVALID_RESULT_TYPE);
        };
    }
}
