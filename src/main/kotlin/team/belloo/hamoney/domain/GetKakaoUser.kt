package team.belloo.hamoney.domain

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.client.KakaoTokenClient
import team.belloo.hamoney.client.KakaoUserClient

@UseCase
class GetKakaoUser(
    private val kakaoTokenClient: KakaoTokenClient,
    private val kakaoUserClient: KakaoUserClient
) {
    operator fun invoke(code: String, state: String): KakaoUser {
        if (state != "hamoney") throw IllegalArgumentException("state not matched. $state")

        val tokenResponse =
            kakaoTokenClient.token(code = code, redirectUri = "http://10.0.2.2:8080/oauth/kakao/code").execute()
                .let { response ->
                    if (!response.isSuccessful) {
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
            isEmailValid = kakaoResult.kakao_account.is_email_valid,
            isEmailVerified = kakaoResult.kakao_account.is_email_verified
        )
    }

    data class KakaoUser(
        val id: Long,
        val email: String,
        val hasEmail: Boolean,
        val isEmailValid: Boolean,
        val isEmailVerified: Boolean
    )
}