package team.belloo.hamoney.client

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface KakaoUserClient {

    @GET("/v2/user/me")
    fun me(
        @Header("Authorization") header: String
    ): Call<MeResponse>

    data class MeResponse(
        val id: Long,
        val has_signed_up: Boolean?,
        val connected_at: String?,
        val synched_at: String?,
        val properties: String?,
        val kakao_account: KakaoAccount
    )

    data class KakaoAccount(
        val has_email: Boolean,
        val email_needs_agreement: Boolean,
        val is_email_valid: Boolean,
        val is_email_verified: Boolean,
        val email: String
    )
}