package team.belloo.hamoney.client

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GoogleUserClient {
    @GET("/oauth2/v3/userinfo")
    fun me(
        @Header("Authorization") accessToken: String
    ): Call<MeResponse>

    data class MeResponse(
        val sub: String, // unique among all google accounts and never reused
        val email: String,
        val gender: String,
        val hd: String,
        val link: String,
        val locale: String,
        val name: String,
        val verified_email: Boolean
    )
}