package com.minsproject.league.repository;

import com.minsproject.league.entity.TeamMember;
import com.minsproject.league.repository.querydsl.TeamMemberCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>, TeamMemberCustomRepository {}