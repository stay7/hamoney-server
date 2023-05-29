package team.belloo.hamoney.core.signup

import team.belloo.hamoney.core.user.User

interface SignupStrategy {
    val type: Type

    operator fun invoke(command: Command): User

    enum class Type {
        NEW_USER, EXISTED_USER, RETRY, CONNECT_SOCIAL
    }

    data class Command(
        val user: User?,
        val email: String,
        val provider: SocialProvider,
        val providerId: String,
        val socialSignupHistory: SocialSignupHistory?
    )
}