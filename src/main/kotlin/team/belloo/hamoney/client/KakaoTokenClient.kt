package team.belloo.hamoney.client

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface KakaoTokenClient {

    @GET("/oauth/authorize")
    fun authorize(
        @Query("client_id") clientId: String = REST_API_KEY,
        @Query("redirect_uri") redirectUri: String = REDIRECT_URI,
        @Query("response_type") responseType: String = "code"
    ): Call<ResponseBody>

    companion object {
        private const val GRANT_TYPE = "authorization_code"
        private const val REST_API_KEY = "048ac415d2e9bb5724bcb08508658463"
        private const val REDIRECT_URI = "/oauth/kakao/code"
        private const val CLIENT_SECRET = "vVx8pRu6fJEZa95PynZEf7xfUr5e0UrL"
        private const val ADMIN_KEY = "3b7c78c35f326355d8d00c20bcdd057f"
    }

    @FormUrlEncoded
    @POST("/oauth/token")
    fun token(
        @Field("grant_type") grantType: String = GRANT_TYPE,
        @Field("client_id") clientId: String = REST_API_KEY,
        @Field("redirect_uri") redirectUri: String,
        @Field("code") code: String,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET
    ): Call<TokenResponse>

    data class TokenResponse(
        val token_type: String,
        val access_token: String,
        val id_token: String?,
        val expires_in: Int,
        val refresh_token: String,
        val refresh_token_expires_in: Int?,
        val scope: String,
    )
}