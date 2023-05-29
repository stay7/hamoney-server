package team.belloo.hamoney.domain.oauth

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.entity.oauth.AccessTokenEntity
import team.belloo.hamoney.entity.oauth.RefreshTokenEntity
import team.belloo.hamoney.persistence.AccessTokenRepository
import team.belloo.hamoney.persistence.RefreshTokenRepository
import java.time.Clock
import java.time.Duration

@UseCase
class IssueOAuthToken(
    private val clock: Clock,
    private val generateOAuthToken: GenerateOAuthToken,
    private val accessTokenRepository: AccessTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    operator fun invoke(user: User): Pair<String, String> {
        val (accessToken, refreshToken) = generateOAuthToken.invoke()
        val now = clock.instant()

        AccessTokenEntity().apply {
            userId = user.id
            token = accessToken
            createdAt = now
            expiredAt = now.plus(Duration.ofDays(28))
        }.also {
            accessTokenRepository.save(it)
        }

        RefreshTokenEntity().apply {
            userId = user.id
            token = refreshToken
            createdAt = now
            expiredAt = now.plus(Duration.ofDays(28))
        }.also {
            refreshTokenRepository.save(it)
        }

        // TODO: 새로 발급한 토큰을 제외하고 기존 토큰들을 삭제해야함

        return accessToken to refreshToken
    }
}