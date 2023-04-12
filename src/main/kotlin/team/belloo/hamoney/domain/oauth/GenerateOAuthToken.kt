package team.belloo.hamoney.domain.oauth

import team.belloo.hamoney.UseCase
import java.util.UUID

@UseCase
object GenerateOAuthToken {

    operator fun invoke(): Pair<String, String> {
        val accessToken = generateToken().take(20)
        val refreshToken = generateToken().take(20)

        return accessToken to refreshToken
    }

    private fun generateToken(): String = (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-", "")
}