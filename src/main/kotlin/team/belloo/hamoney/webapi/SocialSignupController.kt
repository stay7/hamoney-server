package team.belloo.hamoney.webapi

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import team.belloo.hamoney.domain.GetGoogleUser
import team.belloo.hamoney.domain.GetKakaoUser
import team.belloo.hamoney.domain.signup.GetSignupStrategy
import team.belloo.hamoney.domain.signup.SocialProvider

@Controller
@RequestMapping("/social")
class SocialSignupController(
    private val getKakaoUser: GetKakaoUser,
    private val getGoogleUser: GetGoogleUser,
    private val getSignupStrategy: GetSignupStrategy,
) {

    @GetMapping("/kakao/code")
    fun kakao(
        @RequestParam code: String,
        @RequestParam state: String,
    ): String {
        return with(getKakaoUser(code, state)) {
            require(this.hasEmail && this.verifiedEmail) { "카카오 이메일이 없거나 인증되지 않았습니다" }
            getSignupStrategy(this.email, SocialProvider.KAKAO, this.id.toString())
        }.let { (strategy, command) ->
            strategy(command)
        }.let { user ->
            "redirect:hamoney://success?email=${user.email}"
        }
    }

    @GetMapping("/google/code")
    fun google(
        @RequestParam code: String,
        @RequestParam state: String,
    ): String {
        return with(getGoogleUser(code, state)) {
            require(this.verifiedEmail) { "구글 이메일이 없거나 인증되지 않았습니다" }
            getSignupStrategy(this.email, SocialProvider.GOOGLE, this.id)
        }.let { (strategy, command) ->
            strategy(command)
        }.let { user ->
            "redirect:hamoney://success?email=${user.email}"
        }
    }
}