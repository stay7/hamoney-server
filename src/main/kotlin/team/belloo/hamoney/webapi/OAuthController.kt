package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.domain.oauth.IssueOAuthToken
import team.belloo.hamoney.domain.signup.SignupTokenEncoder
import team.belloo.hamoney.persistence.UserRepository

@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val userRepository: UserRepository,
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

    data class IssueForm(
        val email: String,
        val token: String
    )

    data class IssueResult(
        val accessToken: String,
        val refreshToken: String
    ) : JsonResult(status = Status.SUCCESS)
}