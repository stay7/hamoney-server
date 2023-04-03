package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.domain.GetKakaoUser

@RestController
@RequestMapping("/oauth")
class SocialSignupController(
    private val getKakaoUser: GetKakaoUser
) {

    @GetMapping("/kakao/code")
    fun kakao(
        @RequestParam code: String,
        @RequestParam state: String,
    ) {
        getKakaoUser(code, state).also { println(it) }
    }

    @GetMapping("/google/code")
    fun google(
        @RequestParam code: String,
        @RequestParam state: String,
    ) {
        println("$code, $state")
    }
}