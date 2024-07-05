package com.minsproject.league.entity;

import com.minsproject.league.exception.ErrorCode;
import com.minsproject.league.exception.LeagueCustomException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Result extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    private String resultType; // W, D, L

    private Integer points; //3: 승, 1: 무, 0: 패

    private Result(Match match, Team team, String resultType) {
        this.match = match;
        this.team = team;
        this.resultType = resultType;
        this.points = calculatePoints(resultType);
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
