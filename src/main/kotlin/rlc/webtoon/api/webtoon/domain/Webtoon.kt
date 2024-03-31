package rlc.webtoon.api.webtoon.domain

import jakarta.persistence.*
import rlc.webtoon.api.common.domain.BaseEntity
import rlc.webtoon.api.webtoon.domain.value.WebtoonDay

@Table(name = "webtoons")
@Entity
class Webtoon(
        val title: String,
        val content: String,
        val requiredLeaves: Int?,
        val author: String,
        val isFree: Boolean = true,
        val day: WebtoonDay
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

}