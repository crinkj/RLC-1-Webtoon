package rlc.webtoon.api.webtoon.infra

import org.springframework.data.jpa.repository.JpaRepository
import rlc.webtoon.api.webtoon.domain.Webtoon

interface WebtoonRepository : JpaRepository<Webtoon, Long> {

}