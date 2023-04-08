package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.persistence.UserRepository
import java.time.Instant

@RestController
@RequestMapping("/signup")
class SignupController(
    private val userRepository: UserRepository
) {

    @PostMapping
    fun signup(form: SignupForm): JsonResult {
        require(form.email.isNotBlank()) { "email could not blank. email=${form.email}" }
        require(form.nickname.isNotBlank()) { "nickname could not blank. nickname=${form.nickname}" }

        val user = userRepository.findByEmail(form.email) ?: return JsonResult.error()
        user.apply {
            nickname = form.nickname
            signedAt = Instant.now()
        }.also {
            userRepository.save(it)
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