package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.signup.SignupStrategy
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.core.user.UserRepository
import team.belloo.hamoney.persistence.JpaSocialSignupRepository

@UseCase
class ExistedUser(
    private val userRepository: UserRepository,
    private val socialSignupRepository: JpaSocialSignupRepository
) : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.EXISTED_USER

    override operator fun invoke(command: SignupStrategy.Command): User {
        require(command.socialSignupHistory != null && command.user != null)

        return command.user!!
    }
}