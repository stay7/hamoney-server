package team.belloo.hamoney.webapi

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import team.belloo.hamoney.domain.GetGoogleUser
import team.belloo.hamoney.domain.GetKakaoUser

@Controller
@RequestMapping("/oauth")
class SocialSignupController(
    private val getKakaoUser: GetKakaoUser,
    private val getGoogleUser: GetGoogleUser
) {

    @GetMapping("/kakao/code")
    fun kakao(
        @RequestParam code: String,
        @RequestParam state: String,
    ): String {
        getKakaoUser(code, state).also { println(it) }
        return "redirect:hamoney://"
    }

    @GetMapping("/google/code")
    fun google(
        @RequestParam code: String,
        @RequestParam state: String,
    ): String {
        getGoogleUser(code, state).also { println(it) }
        return "redirect:hamoney://"
    }
}