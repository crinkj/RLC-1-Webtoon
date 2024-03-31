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
        val purchasedLeaves: Int,
        @Column(unique = true)
        val impUid: String,
        val purchasedName: String,
        var isRefunded: Boolean = false,
        var refundedAt: Instant?,
        val refundedAmount: Int?,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    companion object {
        fun create(user: User, paidAmount: Int, impUid: String, purchasedName: String, purchasedLeaves: Int) = Payment(
                user = user,
                paidAmount = paidAmount,
                impUid = impUid,
                purchasedName = purchasedName,
                refundedAmount = null,
                refundedAt = null,
                purchasedLeaves = purchasedLeaves
        )
    }

    fun refund(refundedAmount: Int) {
        this.isRefunded = true
        this.refundedAt = Instant.now()
        this.refundedAt = refundedAt
    }
}