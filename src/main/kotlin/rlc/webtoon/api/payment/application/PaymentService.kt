package rlc.webtoon.api.payment.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rlc.webtoon.api.common.client.ApiError
import rlc.webtoon.api.common.client.Error
import rlc.webtoon.api.payment.domain.Payment
import rlc.webtoon.api.payment.infra.PaymentRepository
import rlc.webtoon.api.payment.infra.portone.PortOneService
import rlc.webtoon.api.payment.infra.portone.dto.PortOnePaymentResponse
import rlc.webtoon.api.payment.presentation.dto.PayRequest
import rlc.webtoon.api.payment.presentation.dto.PaymentResponse
import rlc.webtoon.api.user.domain.User
import rlc.webtoon.api.user.infra.UserRepository
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
@Transactional(readOnly = true)
class PaymentService(
        private val userRepository: UserRepository,
        private val portOneService: PortOneService,
        private val paymentRepository: PaymentRepository
) {

    fun charge(accountId: String, request: PayRequest) {
        val user: User = userRepository.findByAccountId(accountId)

        // 결제 진행
        val result: PortOnePaymentResponse =
                try {
                    portOneService.pay(
                            "${request.leafType.name} 결제",
                            card = request.card,
                            amount = request.leafType.price
                    )
                } catch (e: Exception) {
                    throw ApiError(Error.FAILED_PAYMENT)
                }

        // 결제 완료
        if (result.isSuccess()) {
            savePaymentHistory(user, result, request.leafType.quantity)
            user.chargeLeaves(request.leafType.quantity)
        }

        throw ApiError(Error.FAILED_PAYMENT)
    }

    fun getPaymentHistory(accountId: String): List<PaymentResponse> {
        val user = userRepository.findByAccountId(accountId)
        val payments = paymentRepository.findAllByUser(user)

        return payments.map(PaymentResponse.Companion::of)
    }

    fun refund(accountId: String, paymentId: Long): PaymentResponse {
        val payment = paymentRepository.getReferenceById(paymentId)

        val daysBetween = ChronoUnit.DAYS.between(payment.createdAt, Instant.now())
        if (daysBetween > 2) {
            throw ApiError(Error.PASSED_DUE_DATE)
        }

        val requiredRefundAmount = payment.paidAmount * .60
        val result = portOneService.cancel(
                impUid = payment.impUid,
                amount = requiredRefundAmount.toInt(),
                reason = "기한 내 환불")

        payment.refund(result.response!!.cancelAmount)
        return PaymentResponse.of(payment)
    }

    private fun savePaymentHistory(user: User, result: PortOnePaymentResponse, purchasedLeaves: Int) {
        Payment.create(
                user = user,
                paidAmount = result.response?.amount!!,
                impUid = result.response.impUid,
                purchasedName = result.response.name!!,
                purchasedLeaves = purchasedLeaves
        )
    }
}