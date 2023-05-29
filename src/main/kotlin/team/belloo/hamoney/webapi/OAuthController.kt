package team.belloo.hamoney.webapi

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.domain.oauth.IssueOAuthToken
import team.belloo.hamoney.domain.signup.SignupTokenEncoder
import team.belloo.hamoney.domain.user.UserRepository
import team.belloo.hamoney.entity.oauth.RefreshTokenEntity
import team.belloo.hamoney.persistence.RefreshTokenRepository

@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val issueOAuthToken: IssueOAuthToken
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/issue")
    fun issue(
        @RequestBody form: IssueForm
    ): JsonResult {
        if (form.token != SignupTokenEncoder.encode(form.email)) {
            logger.error("${form.token} != ${SignupTokenEncoder.encode(form.email)}")
            return JsonResult.error("일치하지 않는 토큰입니다.")
        }

        val user = userRepository.findByEmail(form.email) ?: return JsonResult.error()

        return issueOAuthToken.invoke(user).let { (accessToken, refreshToken) ->
            IssueResult(accessToken, refreshToken)
        }
    }

    @GetMapping("/refresh")
    fun refresh(@RequestParam("refreshTokenString") refreshTokenString: String): JsonResult {
        val user = refreshTokenRepository.findByToken(refreshTokenString)?.takeIf {
            it.status == RefreshTokenEntity.Status.ACTIVE.value
        }?.let {
            userRepository.findById(it.userId)
        } ?: return JsonResult.error()

        return issueOAuthToken.invoke(user).let {
            IssueResult(it.first, it.second)
        }
    }

    data class IssueForm(
        val email: String,
        val token: String
    )

    data class IssueResult(
        val accessToken: String,
        val refreshToken: String
    ) : JsonResult(status = Status.SUCCESS)
}