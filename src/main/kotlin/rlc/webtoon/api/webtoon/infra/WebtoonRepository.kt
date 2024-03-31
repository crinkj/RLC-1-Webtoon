package rlc.webtoon.api.webtoon.infra

import org.springframework.data.jpa.repository.JpaRepository
import rlc.webtoon.api.webtoon.domain.Webtoon

interface WebtoonRepository : JpaRepository<Webtoon, Long> {

    fun findAllByFree(isFree:Boolean):List<Webtoon>

    fun findAllByFreeOrIdIn(isFree:Boolean,ids:List<Long>):List<Webtoon>
}