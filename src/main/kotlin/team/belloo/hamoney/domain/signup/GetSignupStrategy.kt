package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.signup.SignupStrategy
import team.belloo.hamoney.core.signup.SocialProvider
import team.belloo.hamoney.core.signup.SocialSignupHistoryRepository
import team.belloo.hamoney.core.user.UserRepository

/**
 * email로 찾은 유저 있음 && socialSignupEntity도 있음 -> completedAt이 있으면 ExistedUser, 없으면 Retry
 * email로 찾은 유저 있음 && socialSignupEntity 없음 -> 다른 social로 가입 TODO
 * email로 찾은 유저 없음 && socialSignupEntity 있음 -> Exception
 * email로 찾은 유저 없음 && socialSignupEntity 없음 -> CreateUser
 */
@UseCase
class GetSignupStrategy(
    private val userRepository: UserRepository,
    private val socialSignupRepository: SocialSignupHistoryRepository,
    private val strategies: List<SignupStrategy>
) {
    operator fun invoke(
        email: String,
        provider: SocialProvider,
        providerId: String
    ): Pair<SignupStrategy, SignupStrategy.Command> {
        val strategyByType = strategies.associateBy { it.type }
        val command = SignupStrategy.Command(
            email = email,
            provider = provider,
            providerId = providerId,
            user = null,
            socialSignupHistory = null
        )
        val user = userRepository.findByEmail(email)
        val socialSignupHistory = socialSignupRepository.findByProviderKey(providerKey(provider, providerId))

        return when {
            user == null && socialSignupHistory == null ->
                strategyByType.getValue(SignupStrategy.Type.NEW_USER) to SignupStrategy.Command(
                    email = email,
                    provider = provider,
                    providerId = providerId,
                    user = null,
                    socialSignupHistory = null
                )

            user != null && socialSignupHistory == null -> strategyByType.getValue(SignupStrategy.Type.CONNECT_SOCIAL) to command.copy(
                user = user
            )

            user != null && socialSignupHistory != null -> {
                if (socialSignupHistory.completedAt != null) strategyByType.getValue(SignupStrategy.Type.EXISTED_USER) to command.copy(
                    user = user,
                    socialSignupHistory = socialSignupHistory
                ) else strategyByType.getValue(SignupStrategy.Type.RETRY) to command.copy(
                    user = user,
                    socialSignupHistory = socialSignupHistory
                )
            }

            else -> throw IllegalStateException("strange signup state. ${user} ${socialSignupHistory}")
        }
    }

    private fun providerKey(provider: SocialProvider, providerId: String) = "${provider.value}_${providerId}"
}