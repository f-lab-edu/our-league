package com.minsproject.league.repository;

import com.minsproject.league.entity.Team;
import com.minsproject.league.repository.querydsl.TeamsCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<Team, Long>, TeamsCustomRepository {
}
