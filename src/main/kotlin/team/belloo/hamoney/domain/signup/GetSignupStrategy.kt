package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.persistence.SocialSignupRepository
import team.belloo.hamoney.persistence.UserRepository

@UseCase
class GetSignupStrategy(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupRepository,
    private val strategies: List<SignupStrategy>
) {
    operator fun invoke(
        email: String,
        provider: SocialProvider,
        providerId: String
    ): Pair<SignupStrategy, SignupStrategy.SignupCommand> {
        val strategyByType = strategies.associateBy { it.type }
        val signupCommand = SignupStrategy.SignupCommand(
            email = email,
            provider = provider,
            providerId = providerId,
            userEntity = null,
            socialSignupEntity = null
        )
        val socialSignupHistory = socialSignupRepository.findByProviderKey(providerKey(provider, providerId))
            ?: return strategyByType.getValue(SignupStrategy.Type.NEW_USER) to signupCommand
        val user = userRepository.findById(socialSignupHistory.userId).get()

        if (socialSignupHistory.completedAt == null && user.signedAt == null)
            return strategyByType.getValue(SignupStrategy.Type.RETRY) to signupCommand.copy(
                userEntity = user,
                socialSignupEntity = socialSignupHistory
            )

        return strategyByType.getValue(SignupStrategy.Type.EXISTED_USER) to signupCommand.copy(
            userEntity = user,
            socialSignupEntity = socialSignupHistory
        )
    }

    private fun providerKey(provider: SocialProvider, providerId: String) = "${provider.value}_${providerId}"
}