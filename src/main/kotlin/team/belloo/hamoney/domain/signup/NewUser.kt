package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.signup.SocialSignupEntity
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.persistence.SocialSignupRepository
import team.belloo.hamoney.persistence.UserRepository
import java.util.UUID

@UseCase
class NewUser(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository
) : SignupStrategy {
    override val type: SignupStrategy.Type
        get() = SignupStrategy.Type.NEW_USER

    override fun invoke(command: SignupStrategy.SignupCommand): UserEntity {
        require(command.userEntity == null && command.socialSignupEntity == null)

        val uuid = UUID.randomUUID().toString()
        val user = UserEntity().apply {
            this.uuid = uuid
            this.nickname = "유저_${uuid}"
            this.email = command.email
            this.status = UserEntity.Status.ACTIVE.value
        }.let {
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