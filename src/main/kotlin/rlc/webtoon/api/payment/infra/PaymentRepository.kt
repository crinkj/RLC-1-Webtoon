package rlc.webtoon.api.payment.infra

import org.springframework.data.jpa.repository.JpaRepository
import rlc.webtoon.api.payment.domain.Payment
import rlc.webtoon.api.user.domain.User

interface PaymentRepository : JpaRepository<Payment, Long> {

    fun findAllByUser(user: User):List<Payment>
}