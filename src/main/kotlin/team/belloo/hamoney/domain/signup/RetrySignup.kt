package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.user.UserEntity

@UseCase
class RetrySignup : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.RETRY

    override fun invoke(command: SignupStrategy.SignupCommand): UserEntity {
        require(command.userEntity != null && command.socialSignupEntity != null)

        return command.userEntity
    }
}