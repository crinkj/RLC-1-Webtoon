package rlc.webtoon.api.payment.presentation

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rlc.webtoon.api.auth.Authenticated
import rlc.webtoon.api.auth.presentation.UserAuth
import rlc.webtoon.api.payment.application.PaymentService
import rlc.webtoon.api.payment.presentation.dto.PayRequest

@Tag(name = "결제")
@RestController
@RequestMapping("/payments")
class PaymentController(
        val paymentService: PaymentService
) {

    @PostMapping
    fun payWithLeaves(
            @Authenticated userAuth: UserAuth,
            request: PayRequest
    ) {
        val userAccountId: String = userAuth.accountId

        paymentService.pay(
                accountId = userAccountId,
                request = request
        )

    }
}