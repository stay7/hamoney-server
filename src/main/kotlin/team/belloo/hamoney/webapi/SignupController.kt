package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.domain.oauth.IssueOAuthToken
import team.belloo.hamoney.domain.signup.SignupTokenEncoder
import team.belloo.hamoney.domain.user.UserRepository
import team.belloo.hamoney.persistence.SocialSignupRepository
import java.time.Clock

@RestController
@RequestMapping("/signup")
class SignupController(
    private val clock: Clock,
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository,
    private val issueOAuthToken: IssueOAuthToken
) {

    // 유저는 social login하면 생성된다.
    // signup을 통해서 닉네임을 만든다
    @PostMapping
    fun signup(
        @RequestBody form: SignupForm
    ): JsonResult {
        require(form.email.isNotBlank()) { "email could not blank. email=${form.email}" }
        require(form.nickname.isNotBlank()) { "nickname could not blank. nickname=${form.nickname}" }

        if (form.token != SignupTokenEncoder.encode(form.email))
            return JsonResult.error()

        userRepository.findByNickname(form.nickname)?.let {
            return JsonResult.error("이미 존재하는 닉네임입니다")
        }

        val user = userRepository.findByEmail(form.email) ?: return JsonResult.error("이메일로 유저를 찾을 수 없네요")
        val socialSignupEntity =
            socialSignupRepository.findByEmail(form.email) ?: return JsonResult.error("empty social signup")

        val now = clock.instant()
        user.copy(
            nickname = form.nickname
        ).also {
            userRepository.save(it)
        }

        socialSignupEntity.apply {
            completedAt = now
        }.also {
            socialSignupRepository.save(it)
        }

        val (accessToken, refreshToken) = issueOAuthToken.invoke(user)

        return SignupResult(
            id = user.uuid,
            email = user.email,
            nickname = user.nickname,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    data class SignupResult(
        val id: String,
        val email: String,
        val nickname: String,
        val accessToken: String,
        val refreshToken: String
    ) : JsonResult(status = Status.SUCCESS)

    data class SignupForm(
        val email: String,
        val nickname: String,
        val token: String
    )
}