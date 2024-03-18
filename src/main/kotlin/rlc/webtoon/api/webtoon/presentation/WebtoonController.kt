package rlc.webtoon.api.webtoon.presentation

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rlc.webtoon.api.webtoon.application.WebtoonService

@Tag(name = "웹툰")
@RestController
@RequestMapping("/webtoons")
class WebtoonController(
    private val webtoonService: WebtoonService
) {
    @GetMapping
    fun get(){

    }

}