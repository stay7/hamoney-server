package team.belloo.hamoney.webapi

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import team.belloo.hamoney.domain.GetGoogleUser
import team.belloo.hamoney.domain.GetKakaoUser
import team.belloo.hamoney.domain.signup.ExistedUser
import team.belloo.hamoney.domain.signup.Signup
import team.belloo.hamoney.domain.signup.SignupCommand
import team.belloo.hamoney.domain.signup.SocialProvider

@Controller
@RequestMapping("/oauth")
class SocialSignupController(
    private val getKakaoUser: GetKakaoUser,
    private val getGoogleUser: GetGoogleUser,
    private val signup: Signup,
    private val existedUser: ExistedUser
) {

    @GetMapping("/kakao/code")
    fun kakao(
        @RequestParam code: String,
        @RequestParam state: String,
    ): String {
        return with(getKakaoUser(code, state)) {
            require(this.hasEmail && this.verifiedEmail) { "카카오 이메일이 없거나 인증되지 않았습니다" }
            SignupCommand(email, SocialProvider.KAKAO, id.toString())
        }.let {
            existedUser(it) ?: signup(it)
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
            SignupCommand(email, SocialProvider.GOOGLE, id)
        }.let {
            existedUser(it) ?: signup(it)
        }.let { user ->
            "redirect:hamoney://success?email=${user.email}"
        }
    }
}