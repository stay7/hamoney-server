package team.belloo.hamoney.persistence

import team.belloo.hamoney.Repository
import team.belloo.hamoney.core.signup.SocialSignupHistory
import team.belloo.hamoney.core.signup.SocialSignupHistoryRepository
import team.belloo.hamoney.entity.signup.SocialSignupEntity

@Repository
class JdbcSocialSignupHistoryRepository(
    private val jpaSocialSignupHistoryRepository: JpaSocialSignupRepository,
) : SocialSignupHistoryRepository {
    override fun findByEmail(email: String): SocialSignupHistory? {
        return jpaSocialSignupHistoryRepository.findByEmail(email)?.toDomain()
    }

    override fun findByProviderKey(providerKey: String): SocialSignupHistory? {
        return jpaSocialSignupHistoryRepository.findByProviderKey(providerKey)?.toDomain()
    }

    override fun save(socialSignupHistory: SocialSignupHistory): SocialSignupHistory {
        return jpaSocialSignupHistoryRepository.save(socialSignupHistory.toEntity()).toDomain()
    }

    private fun SocialSignupHistory.toEntity() = SocialSignupEntity().apply {
        id = this@toEntity.id
        userId = this@toEntity.userId
        email = this@toEntity.email
        providerKey = this@toEntity.providerKey
        createdAt = this@toEntity.createdAt
        completedAt = this@toEntity.completedAt
    }

    private fun SocialSignupEntity.toDomain() = SocialSignupHistory(
        id = this.id,
        userId = this.userId,
        email = this.email,
        providerKey = this.providerKey,
        createdAt = this.createdAt,
        completedAt = this.completedAt,
    )
}
