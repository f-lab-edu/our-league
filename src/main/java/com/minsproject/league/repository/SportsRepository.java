package com.minsproject.league.repository;

import com.minsproject.league.entity.Sports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportsRepository extends JpaRepository<Sports, Long> {
}
