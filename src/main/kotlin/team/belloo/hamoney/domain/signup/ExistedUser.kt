package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.SocialSignupRepository
import team.belloo.hamoney.persistence.UserRepository

@UseCase
class ExistedUser(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository
) : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.EXISTED_USER

    override operator fun invoke(signupCommand: SignupStrategy.SignupCommand): UserEntity {
        require(signupCommand.socialSignupEntity != null && signupCommand.userEntity != null)

        return signupCommand.userEntity
    }
}