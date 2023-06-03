package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.signup.SignupStrategy
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.core.user.UserRepository
import team.belloo.hamoney.entity.signup.SocialSignupEntity
import team.belloo.hamoney.persistence.JpaSocialSignupRepository
import java.time.Clock
import java.util.UUID

@UseCase
class NewUser(
    private val userRepository: UserRepository,
    private val socialSignupRepository: JpaSocialSignupRepository,
    private val clock: Clock
) : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.NEW_USER

    override fun invoke(command: SignupStrategy.Command): User {
        val uuid = UUID.randomUUID().toString()

        val user = User(
            id = 0,
            uuid = uuid,
            nickname = "유저_${uuid}",
            email = command.email,
            status = User.Status.ACTIVE,
            createdAt = clock.instant(),
            updatedAt = clock.instant()
        ).let {
            userRepository.save(it)
        }

        SocialSignupEntity().apply {
            userId = user.id
            email = command.email
            providerKey = providerKey(command.provider, command.providerId)
        }.let {
            socialSignupRepository.save(it)
        }
        return user
    }
}