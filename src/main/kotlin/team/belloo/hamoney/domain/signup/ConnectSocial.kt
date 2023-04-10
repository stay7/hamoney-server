package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.SocialSignupEntity
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.SocialSignupRepository
import java.time.Clock

@UseCase
class ConnectSocial(
    private val clock: Clock,
    private val socialSignupRepository: SocialSignupRepository
) : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.CONNECT_SOCIAL

    override fun invoke(command: SignupStrategy.SignupCommand): UserEntity {
        require(command.userEntity != null && command.socialSignupEntity == null)

        SocialSignupEntity().apply {
            userId = command.userEntity.id
            email = command.email
            providerKey = providerKey(command.provider, command.providerId)
            completedAt = clock.instant()
        }.let {
            socialSignupRepository.save(it)
        }
        return command.userEntity
    }
}