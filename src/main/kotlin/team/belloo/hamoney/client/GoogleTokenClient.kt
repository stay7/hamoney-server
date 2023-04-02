package team.belloo.hamoney.client

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GoogleTokenClient {

    companion object {
        private const val CLIENT_ID = "909621199681-voruk7in7eaiu8i2fpus2lf9gcrclfoj.apps.googleusercontent.com"
        private const val CLIENT_SECRET = "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"
        private const val REDIRECT_URI = "/oauth/google/code"
        private const val GRANT_TYPE = "authorization_code"
    }

    @FormUrlEncoded
    @POST("/token")
    fun token(
        @Field("client_id") clientId: String = CLIENT_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET,
        @Field("code") code: String,
        @Field("grant_type") grantType: String = GRANT_TYPE,
        @Field("redirect_uri") redirectUri: String
    ): Call<TokenResponse>

    data class TokenResponse(
        val access_token: String,
        val expires_in: String,
        val id_token: String,
        val scope: String,
        val token_type: String
    )
}