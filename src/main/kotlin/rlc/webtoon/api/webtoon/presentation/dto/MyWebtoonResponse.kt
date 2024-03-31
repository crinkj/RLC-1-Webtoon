package rlc.webtoon.api.webtoon.presentation.dto

import rlc.webtoon.api.user.domain.UserWebtoon
import rlc.webtoon.api.webtoon.domain.Webtoon

data class MyWebtoonResponse(
        val title: String,
        val paidLeaves: Int?
) {
    companion object {
        fun of(webtoon:UserWebtoon) = MyWebtoonResponse(
                title = webtoon.title,
                paidLeaves = webtoon.paidLeaves
        )
    }
}