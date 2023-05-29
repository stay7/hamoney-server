package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.signup.SignupStrategy
import team.belloo.hamoney.core.user.User

@UseCase
class RetrySignup : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.RETRY

    override fun invoke(command: SignupStrategy.Command): User {
        require(command.user != null && command.socialSignupHistory != null)

        return command.user!!
    }
}