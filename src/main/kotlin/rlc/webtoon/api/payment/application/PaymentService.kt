package rlc.webtoon.api.payment.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import rlc.webtoon.api.payment.infra.portone.PortOneService
import rlc.webtoon.api.payment.infra.portone.dto.PortOnePaymentResponse
import rlc.webtoon.api.payment.presentation.dto.PayRequest
import rlc.webtoon.api.user.domain.User
import rlc.webtoon.api.user.infra.UserRepository

@Service
@Transactional(readOnly = true)
class PaymentService(
        private val userRepository: UserRepository,
        private val portOneService: PortOneService
) {

    fun pay(accountId: String, request: PayRequest) {
        val user: User = userRepository.findByAccountId(accountId)

        // 결제 진행
        val result: PortOnePaymentResponse = portOneService.pay(
                "${request.leafType.name} 결제",
                card = request.card,
                amount = request.leafType.price
        )

        // 결제 완료
        if (result.isSuccess()) {

        }

        // DB 결제 정보 저장
    }

    private fun saveHistory(){

    }
}