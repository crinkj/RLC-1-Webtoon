package rlc.webtoon.api.webtoon.application

import org.springframework.stereotype.Service
import rlc.webtoon.api.webtoon.infra.WebtoonRepository
import rlc.webtoon.api.webtoon.presentation.dto.WebtoonResponse

@Service
class WebtoonService(
        private val webtoonRepository: WebtoonRepository
) {
    fun get(): List<WebtoonResponse> {
        val webtoons = webtoonRepository.findAll()
        return webtoons.map(WebtoonResponse.Companion::of)
    }

    fun getById(id: Long): WebtoonResponse {
        val webtoon = webtoonRepository.getReferenceById(id)
        return WebtoonResponse.of(webtoon)
    }

    fun buy(){

    }
}