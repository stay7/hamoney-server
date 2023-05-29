package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.user.User

@UseCase
class RetrySignup : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.RETRY

    override fun invoke(command: SignupStrategy.SignupCommand): User {
        require(command.user != null && command.socialSignupEntity != null)

        return command.user
    }
}