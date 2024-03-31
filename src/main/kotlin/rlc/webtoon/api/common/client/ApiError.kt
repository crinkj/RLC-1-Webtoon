package rlc.webtoon.api.common.client

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

class ApiError(val error: Error, customMessage: String? = null) : RuntimeException(customMessage ?: error.message)

enum class Error(val status: HttpStatus, val message: String = "") {
    DUPLICATED_ACCOUNT_ID(HttpStatus.ALREADY_REPORTED, "이미 존재하는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    BLANK_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호 공백 불가합니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자 입니다."),
    FAILED_PAYMENT(HttpStatus.INTERNAL_SERVER_ERROR,"결제가 실패하였습니다."),
    REQUIRED_TO_BUY(HttpStatus.UNAUTHORIZED,"결제가 필요한 웹툰입니다."),
    PASSED_DUE_DATE(HttpStatus.BAD_REQUEST,"이미 환불일이 지났습니다.")

}