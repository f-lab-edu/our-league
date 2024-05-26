package com.minsproject.league.service

import com.minsproject.league.dto.TeamSearchDTO
import com.minsproject.league.repository.SportsRepository
import com.minsproject.league.repository.TeamRepository
import spock.lang.Specification
import spock.lang.Subject

class TeamServiceTest extends Specification {

    def teamRepository = Mock(TeamRepository)
    def sportRepository = Mock(SportsRepository)

    @Subject
    def teamService = new TeamService(teamRepository, sportRepository)

    def "TeamSearchDTO의 pageSize가 없으면 10을 기본값으로 잡는다"() {

        given: "pageSize가 null인 경우"
        def searchDTO = TeamSearchDTO.of(null, 101)

        expect: "pageSize는 기본값인 10으로 잡혀야한다"
        10 == searchDTO.getPageSize()
    }

    def "TeamSearchDTO의 pageSize 매개변수가 있으면 받은 매개변수를 사용한다"() {

        given: "pageSize를 받는 경우"
        def searchDTO = TeamSearchDTO.of(20, 101)

        expect: "pageSize는 20으로 잡혀야한다"
        20 == searchDTO.getPageSize()
    }
}
