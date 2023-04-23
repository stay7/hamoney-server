package team.belloo.hamoney.domain.oauth

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.AccessTokenEntity
import team.belloo.hamoney.entity.RefreshTokenEntity
import team.belloo.hamoney.entity.UserEntity
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

    operator fun invoke(userEntity: UserEntity): Pair<String, String> {
        val (accessToken, refreshToken) = generateOAuthToken.invoke()
        val now = clock.instant()

        AccessTokenEntity().apply {
            userId = userEntity.id
            token = accessToken
            createdAt = now
            expiredAt = now.plus(Duration.ofDays(28))
        }.also {
            accessTokenRepository.save(it)
        }

        RefreshTokenEntity().apply {
            userId = userEntity.id
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