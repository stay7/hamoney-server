package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.SocialSignupEntity
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.SocialSignupRepository
import team.belloo.hamoney.persistence.UserRepository
import java.util.UUID

@UseCase
class Signup(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository
) {
    operator fun invoke(signupCommand: SignupCommand): UserEntity {
        val uuid = UUID.randomUUID().toString()
        val user = UserEntity().apply {
            this.uuid = uuid
            this.nickname = "유저_${uuid}"
            this.email = signupCommand.email
            this.status = UserEntity.Status.ACTIVE.value
        }.let {
            userRepository.save(it)
        }

        SocialSignupEntity().apply {
            userId = user.id
            providerKey = "kakao_${signupCommand.providerId}"
        }.let {
            socialSignupRepository.save(it)
        }
        return user
    }
}