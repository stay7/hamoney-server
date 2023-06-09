package team.belloo.hamoney.domain

import org.slf4j.LoggerFactory
import team.belloo.hamoney.client.KakaoTokenClient
import team.belloo.hamoney.client.KakaoUserClient

class GetKakaoUser(
    private val kakaoTokenClient: KakaoTokenClient,
    private val kakaoUserClient: KakaoUserClient,
    private val baseUrl: String
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    operator fun invoke(code: String, state: String): KakaoUser {
        if (state != "hamoney") throw IllegalArgumentException("state not matched. $state")

        logger.info("RedirectUrl: ${baseUrl}/social/kakao/code")

        val tokenResponse =
            kakaoTokenClient.token(code = code, redirectUri = "${baseUrl}/social/kakao/code").execute()
                .let { response ->
                    if (!response.isSuccessful) {
                        logger.error(response.toString())
                        throw IllegalStateException(response.errorBody()?.string())
                    }
                    response.body()
                }

        val kakaoResult =
            kakaoUserClient.me(header = "Bearer ${tokenResponse?.access_token}").execute().let { response ->
                if (!response.isSuccessful) {
                    throw IllegalStateException(response.errorBody()?.string())
                }
                response.body()!!
            }
        return KakaoUser(
            id = kakaoResult.id,
            email = kakaoResult.kakao_account.email,
            hasEmail = kakaoResult.kakao_account.has_email,
            verifiedEmail = kakaoResult.kakao_account.is_email_valid && kakaoResult.kakao_account.is_email_verified,
        )
    }

    data class KakaoUser(
        val id: Long,
        val email: String,
        val hasEmail: Boolean,
        val verifiedEmail: Boolean
    )
}