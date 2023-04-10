package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.entity.SocialSignupEntity
import team.belloo.hamoney.entity.UserEntity

interface SignupStrategy {
    val type: Type

    operator fun invoke(command: SignupCommand): UserEntity

    enum class Type {
        NEW_USER, EXISTED_USER, RETRY, CONNECT_SOCIAL
    }

    data class SignupCommand(
        val email: String,
        val provider: SocialProvider,
        val providerId: String,
        val userEntity: UserEntity?,
        val socialSignupEntity: SocialSignupEntity?
    )
}

enum class SocialProvider(val value: String) {
    KAKAO("kakao"), GOOGLE("google"), APPLE("apple")
}