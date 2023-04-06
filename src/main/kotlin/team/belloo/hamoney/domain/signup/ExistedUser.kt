package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.SocialSignupRepository
import team.belloo.hamoney.persistence.UserRepository

@UseCase
class ExistedUser(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository
) {
    operator fun invoke(signupCommand: SignupCommand): UserEntity? {
        return socialSignupRepository.findByProviderKey(signupCommand.providerKey())?.let {
            userRepository.findById(it.userId).get()
        }
    }

    private fun SignupCommand.providerKey() = "${provider.value}_${providerId}"
}