package rlc.webtoon.api.webtoon.presentation.dto

import rlc.webtoon.api.webtoon.domain.Webtoon

data class WebtoonResponse(
        val id: Long,
        val title: String,
        val content: String,
        val price: Int?,
        val author: String,
        val isFree: Boolean
) {
    companion object {
        fun of(webtoon: Webtoon) = WebtoonResponse(
                id = webtoon.id,
                title = webtoon.title,
                content = webtoon.content,
                price = webtoon.requiredLeaves,
                author = webtoon.author,
                isFree = webtoon.isFree
        )
    }
}