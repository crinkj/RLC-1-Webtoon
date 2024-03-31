package rlc.webtoon.api.payment.presentation

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import rlc.webtoon.api.auth.Authenticated
import rlc.webtoon.api.auth.presentation.UserAuth
import rlc.webtoon.api.common.client.ApiResponse
import rlc.webtoon.api.payment.application.PaymentService
import rlc.webtoon.api.payment.presentation.dto.PayRequest
import rlc.webtoon.api.payment.presentation.dto.PaymentResponse

@Tag(name = "결제")
@RestController
@RequestMapping("/payments")
class PaymentController(
        val paymentService: PaymentService
) {

    @PostMapping
    fun charge(
            @Authenticated userAuth: UserAuth,
            request: PayRequest
    ): ApiResponse<Int> {
        val userAccountId: String = userAuth.accountId

        paymentService.charge(
                accountId = userAccountId,
                request = request
        )

        return ApiResponse(request.leafType.quantity)
    }

    @GetMapping
    fun getPayments(
            @Authenticated userAuth: UserAuth
    ): ApiResponse<List<PaymentResponse>> {
        val accountId: String = userAuth.accountId

        return ApiResponse(paymentService.getPaymentHistory(accountId))
    }

    @PatchMapping("{payentId}/refund")
    fun refund(
            @Authenticated userAuth: UserAuth,
            @PathVariable paymentId: Long
    ): ApiResponse<PaymentResponse> {
        val accountId: String = userAuth.accountId

        return ApiResponse(paymentService.refund(accountId, paymentId))
    }
}