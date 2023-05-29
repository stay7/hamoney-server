package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.user.User
import team.belloo.hamoney.domain.user.UserRepository
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.persistence.SocialSignupRepository

@UseCase
class ExistedUser(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository
) : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.EXISTED_USER

    override operator fun invoke(signupCommand: SignupStrategy.SignupCommand): User {
        require(signupCommand.socialSignupEntity != null && signupCommand.user != null)

        return signupCommand.user
    }
}