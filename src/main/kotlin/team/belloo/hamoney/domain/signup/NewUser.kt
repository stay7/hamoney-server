package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.user.User
import team.belloo.hamoney.domain.user.UserRepository
import team.belloo.hamoney.entity.signup.SocialSignupEntity
import team.belloo.hamoney.persistence.SocialSignupRepository
import java.time.Clock
import java.util.UUID

@UseCase
class NewUser(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository,
    private val clock: Clock
) : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.NEW_USER

    override fun invoke(command: SignupStrategy.SignupCommand): User {
        require(command.user == null && command.socialSignupEntity == null)

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