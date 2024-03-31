package rlc.webtoon.api.webtoon.presentation

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import rlc.webtoon.api.auth.Authenticated
import rlc.webtoon.api.auth.presentation.UserAuth
import rlc.webtoon.api.common.client.ApiResponse
import rlc.webtoon.api.webtoon.application.WebtoonService
import rlc.webtoon.api.webtoon.presentation.dto.MyWebtoonResponse
import rlc.webtoon.api.webtoon.presentation.dto.WebtoonResponse

@Tag(name = "웹툰")
@RestController
@RequestMapping("/webtoons")
class WebtoonController(
        private val webtoonService: WebtoonService
) {

    // 요구사항 4. 웹툰 결제 상세 조회 API
    @PostMapping("/{webtoonId}/buy")
    fun buy(
            @Authenticated userAuth: UserAuth,
            @PathVariable webtoonId: Long
    ): ApiResponse<Int> {
        val accountId: String = userAuth.accountId
        val result = webtoonService.buy(accountId, webtoonId)

        return ApiResponse(result)
    }
    
    // 요구사항 6. 볼 수 있는 웹툰 조회(무료 + 결제한 웹툰)
    @GetMapping
    fun get(@Authenticated userAuth: UserAuth?): ApiResponse<List<WebtoonResponse>> {
        val accountId: String? = userAuth?.accountId
        val result: List<WebtoonResponse> = webtoonService.getAll(accountId)

        return ApiResponse(result)
    }

    // 요구사항 7. 결제한 웹툰 구매 조회 API
    @GetMapping("/my")
    fun getMyPaidWebtoon(
            @Authenticated userAuth: UserAuth
    ): ApiResponse<List<MyWebtoonResponse>> {
        val accountId: String = userAuth.accountId
        val result: List<MyWebtoonResponse> = webtoonService.getMyPaidWebtoon(accountId)

        return ApiResponse(result)
    }

    // 요구사항 8. 웹툰 상세 조회
    @GetMapping("/{webtoonId}")
    fun getById(
            @Authenticated userAuth: UserAuth,
            @PathVariable webtoonId: Long
    ): ApiResponse<WebtoonResponse> {
        val accountId: String = userAuth.accountId
        val result: WebtoonResponse = webtoonService.validateAndGetById(accountId, webtoonId)

        return ApiResponse(result)
    }

}