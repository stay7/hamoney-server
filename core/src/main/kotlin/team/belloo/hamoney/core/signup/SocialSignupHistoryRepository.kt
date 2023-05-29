package team.belloo.hamoney.core.signup

interface SocialSignupHistoryRepository {
    fun findByEmail(email: String): SocialSignupHistory?

    fun findByProviderKey(providerKey: String): SocialSignupHistory?

    fun save(socialSignupHistory: SocialSignupHistory): SocialSignupHistory
}