package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.domain.oauth.IssueOAuthToken
import team.belloo.hamoney.domain.signup.SignupTokenEncoder
import team.belloo.hamoney.entity.RefreshTokenEntity
import team.belloo.hamoney.persistence.RefreshTokenRepository
import team.belloo.hamoney.persistence.UserRepository

@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val issueOAuthToken: IssueOAuthToken
) {

    @PostMapping("/issue")
    fun issue(form: IssueForm): JsonResult {
        if (form.email != SignupTokenEncoder.encode(form.token))
            return JsonResult.error()

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
            userRepository.findById(it.userId).orElse(null)
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