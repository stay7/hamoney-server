package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/signup")
class SocialSignupController {

    @GetMapping("/kakao/code")
    fun kakao(
        @RequestParam code: String,
        @RequestParam state: String,
    ) {

    }
}