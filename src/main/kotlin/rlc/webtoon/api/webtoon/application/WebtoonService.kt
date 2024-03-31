package rlc.webtoon.api.webtoon.application

import org.springframework.stereotype.Service
import rlc.webtoon.api.common.client.ApiError
import rlc.webtoon.api.common.client.Error
import rlc.webtoon.api.user.domain.User
import rlc.webtoon.api.user.infra.UserRepository
import rlc.webtoon.api.webtoon.domain.Webtoon
import rlc.webtoon.api.webtoon.infra.WebtoonRepository
import rlc.webtoon.api.webtoon.presentation.dto.MyWebtoonResponse
import rlc.webtoon.api.webtoon.presentation.dto.WebtoonResponse
import java.time.Instant

@Service
class WebtoonService(
        private val userRepository: UserRepository,
        private val webtoonRepository: WebtoonRepository
) {
    fun getAll(accountId: String?): List<WebtoonResponse> {
        val isFree = true

        val availableWebtoons =
                if (accountId != null) {
                    val user = userRepository.findByAccountId(accountId)
                    val myWebtoonIds: List<Long> = user.myWebtoons.filter { it.endedAt.isBefore(Instant.now()) }
                            .map { it.webtoonId }

                    webtoonRepository.findAllByFreeOrIdIn(isFree, myWebtoonIds)
                } else {
                    webtoonRepository.findAllByFree(isFree)
                }

        return availableWebtoons.map(WebtoonResponse.Companion::of)
    }

    fun getMyPaidWebtoon(accountId: String): List<MyWebtoonResponse> {
        val user = userRepository.findByAccountId(accountId)

        return user.myWebtoons.map(MyWebtoonResponse.Companion::of)
    }

    fun validateAndGetById(accountId: String, id: Long): WebtoonResponse {
        val user = userRepository.findByAccountId(accountId)
        val webtoon = webtoonRepository.getReferenceById(id)

        if (validateMyWebtoon(webtoon, user))
            return WebtoonResponse.of(webtoon)

        throw ApiError(Error.REQUIRED_TO_BUY)
    }

    private fun validateMyWebtoon(webtoon: Webtoon, user: User): Boolean {
        return (webtoon.isFree || user.myWebtoons.any { it.webtoonId == webtoon.id })
    }

    fun buy(accountId: String, webtoonId: Long): Int {
        val user = userRepository.findByAccountId(accountId)
        val webtoon = webtoonRepository.getReferenceById(webtoonId)

        val requiredLeaves = webtoon.requiredLeaves ?: 0

        return user.buyWebtoonWithLeaves(requiredLeaves, webtoon)
    }
}