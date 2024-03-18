package rlc.webtoon.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import rlc.webtoon.api.auth.presentation.JwtArgumentResolver

@Configuration
class WebConfig(
        private val jwtArgumentResolver: JwtArgumentResolver,
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600L)
        super.addCorsMappings(registry)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(jwtArgumentResolver)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
    }
}