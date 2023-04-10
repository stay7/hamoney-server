package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.persistence.SocialSignupRepository
import team.belloo.hamoney.persistence.UserRepository
import java.time.Instant

@RestController
@RequestMapping("/signup")
class SignupController(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository
) {

    @PostMapping
    fun signup(form: SignupForm): JsonResult {
        require(form.email.isNotBlank()) { "email could not blank. email=${form.email}" }
        require(form.nickname.isNotBlank()) { "nickname could not blank. nickname=${form.nickname}" }

        val user = userRepository.findByEmail(form.email) ?: return JsonResult.error()
        val socialSignupEntity = socialSignupRepository.findByEmail(form.email) ?: return JsonResult.error()

        val now = Instant.now()
        user.apply {
            nickname = form.nickname
            signedAt = now
        }.also {
            userRepository.save(it)
        }

        socialSignupEntity.apply {
            completedAt = now
        }.also {
            socialSignupRepository.save(it)
        }

        return SignupResult(
            id = user.uuid,
            email = user.email,
            nickname = user.nickname
        )
    }

    data class SignupResult(
        val id: String,
        val email: String,
        val nickname: String
    ) : JsonResult(status = Status.SUCCESS)

    data class SignupForm(
        val email: String,
        val nickname: String
    )
}