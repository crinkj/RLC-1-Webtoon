package rlc.webtoon.api.payment.domain

import jakarta.persistence.*
import rlc.webtoon.api.common.domain.BaseEntity
import rlc.webtoon.api.user.domain.User
import java.time.Instant

@Entity
@Table(name = "payments")
class Payment(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "userId")
        val user: User,
        val paidAmount: Int,
        @Column(unique = true)
        val impUid: String,
        val purchasedName: String,
        val isRefunded: Boolean = false,
        val refundedAt: Instant?,
        val remainedAmount: Int?,
        val refundedAmount: Int?,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}