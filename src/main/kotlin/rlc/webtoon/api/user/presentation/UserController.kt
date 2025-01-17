package rlc.webtoon.api.user.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rlc.webtoon.api.common.client.ApiResponse
import rlc.webtoon.api.user.application.UserService
import rlc.webtoon.api.user.presentation.dto.SignUpRequest

@Tag(name = "유저")
@RestController
@RequestMapping("/users")
class UserController(
        private val userService: UserService
) {
    @PostMapping("/sign-up")
    @Operation(summary = "회원 가입")
    fun signUp(@RequestBody request: SignUpRequest): ApiResponse<Unit> {
        return ApiResponse(userService.signUp(request))
    }

}