package com.minsproject.league.repository;

import com.minsproject.league.entity.Match;
import com.minsproject.league.repository.querydsl.MatchCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long>, MatchCustomRepository {
}
