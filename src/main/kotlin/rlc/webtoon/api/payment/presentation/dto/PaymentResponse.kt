package rlc.webtoon.api.payment.presentation.dto

import rlc.webtoon.api.payment.domain.Payment
import java.time.Instant

data class PaymentResponse(
        val paymentId: Long,
        val purchasedName: String,
        val paidAmount: Int,
        val purchasedLeaves: Int,
        val isRefunded: Boolean,
        val refundedAmount: Int?,
        val refundedAt: Instant?
) {
    companion object {
        fun of(payment: Payment) = PaymentResponse(
                paymentId = payment.id,
                purchasedName = payment.purchasedName,
                paidAmount = payment.paidAmount,
                purchasedLeaves = payment.purchasedLeaves,
                isRefunded = payment.isRefunded,
                refundedAt = payment.refundedAt,
                refundedAmount = payment.refundedAmount
        )
    }
}