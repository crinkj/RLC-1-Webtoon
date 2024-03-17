package rlc.webtoon.api.payment.domain.value

enum class LeafType(val price: Int) {
    ONE_HUNDRED_LEAVES(100),
    THREE_HUNDRED_LEAVES(300),
    FIVE_HUNDRED_LEAVES(500)
}