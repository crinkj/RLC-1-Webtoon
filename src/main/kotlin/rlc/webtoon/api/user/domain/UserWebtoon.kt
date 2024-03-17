package rlc.webtoon.api.user.domain

import jakarta.persistence.*
import rlc.webtoon.api.common.domain.BaseEntity

@Entity
@Table(name = "userWebtoons")
class UserWebtoon(
        @ManyToOne(fetch = FetchType.LAZY)
        val user: User,
        val webtoonId: Long,
        val title: String,
        val price: Int
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}