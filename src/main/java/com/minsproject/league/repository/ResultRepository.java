package com.minsproject.league.repository;

import com.minsproject.league.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findByMatch_matchIdAndTeam_teamId(Long matchId, Long teadId);
}
