package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.signup.SignupStrategy
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.entity.signup.SocialSignupEntity
import team.belloo.hamoney.persistence.JpaSocialSignupRepository
import java.time.Clock

@UseCase
class ConnectSocial(
    private val clock: Clock,
    private val socialSignupRepository: JpaSocialSignupRepository
) : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.CONNECT_SOCIAL

    override fun invoke(command: SignupStrategy.Command): User {
        checkNotNull(command.user)
        checkNotNull(command.socialSignupHistory)

        SocialSignupEntity().apply {
            userId = command.user!!.id
            email = command.email
            providerKey = providerKey(command.provider, command.providerId)
            completedAt = clock.instant()
        }.let {
            socialSignupRepository.save(it)
        }
        return command.user!!
    }
}