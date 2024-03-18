package rlc.webtoon.api.payment.presentation.dto

import rlc.webtoon.api.common.util.CardValidator
import rlc.webtoon.api.payment.domain.value.LeafType

data class PayRequest(
        val leafType: LeafType,
        val card: PayCard
)

data class PayCard(
        val cardNumber: String,
        val pw2digit: String,
        val expiry: String,
        val birth: String
) {
    init {
        CardValidator.validateUserCard(
                birth = birth,
                expiry = expiry,
                pw2digit = pw2digit,
                cardNumber = cardNumber
        )
    }
}