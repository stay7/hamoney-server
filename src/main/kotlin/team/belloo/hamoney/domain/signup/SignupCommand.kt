package team.belloo.hamoney.domain.signup

data class SignupCommand(
    val email: String,
    val provider: SocialProvider,
    val providerId: String
)

enum class SocialProvider(val value: String) {
    KAKAO("kakao"), GOOGLE("google"), APPLE("apple")
}