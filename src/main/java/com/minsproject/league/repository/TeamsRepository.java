package com.minsproject.league.repository;

import com.minsproject.league.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<Teams, Long> {
}
