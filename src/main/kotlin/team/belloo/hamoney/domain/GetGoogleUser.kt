package team.belloo.hamoney.domain

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.client.GoogleTokenClient
import team.belloo.hamoney.client.GoogleUserClient

@UseCase
class GetGoogleUser(
    private val googleUserClient: GoogleUserClient,
    private val googleTokenClient: GoogleTokenClient
) {
    operator fun invoke(code: String, state: String): GoogleUser {
        val tokenResponse =
            googleTokenClient.token(code = code, redirectUri = "http://10.0.2.2:8080/social/google/code")
                .execute().let { response ->
                    if (!response.isSuccessful) {
                        throw IllegalStateException(response.errorBody()?.string())
                    }
                    response.body()!!
                }

        val googleResult =
            googleUserClient.me(accessToken = "Bearer ${tokenResponse.access_token}").execute().let { response ->
                if (!response.isSuccessful) {
                    throw IllegalStateException(response.errorBody()?.string())
                }
                response.body()!!
            }

        return GoogleUser(
            email = googleResult.email,
            id = googleResult.sub,
            verifiedEmail = googleResult.verified_email
        )
    }

    data class GoogleUser(
        val id: String,
        val email: String,
        val verifiedEmail: Boolean
    )
}