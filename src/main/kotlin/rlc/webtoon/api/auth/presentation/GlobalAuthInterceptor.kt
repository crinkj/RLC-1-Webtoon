package rlc.webtoon.api.auth.presentation

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import rlc.webtoon.api.auth.application.JwtService
import rlc.webtoon.api.common.client.ApiError
import rlc.webtoon.api.common.client.Error.*

@Component
class GlobalAuthInterceptor(
        private val jwtService: JwtService
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val jwt:String = jwtService.getJwtFromRequest(request)

        if (jwt.isEmpty()) {
            throw ApiError(INVALID_JWT)
        }

        return super.preHandle(request, response, handler)
    }
}