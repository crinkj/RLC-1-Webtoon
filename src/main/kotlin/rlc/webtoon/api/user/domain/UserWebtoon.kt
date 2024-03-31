package rlc.webtoon.api.user.domain

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import rlc.webtoon.api.common.domain.BaseEntity
import java.time.Instant
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "userWebtoons")
class UserWebtoon(
        @ManyToOne(fetch = FetchType.LAZY)
        val user: User,
        val webtoonId: Long,
        val title: String,
        val paidLeaves: Int?,
        val endedAt: Instant = Instant.now().plus(2, ChronoUnit.WEEKS)
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}