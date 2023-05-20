package team.belloo.hamoney.webapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.belloo.hamoney.client.GoogleTokenClient
import team.belloo.hamoney.client.GoogleUserClient
import team.belloo.hamoney.client.KakaoTokenClient
import team.belloo.hamoney.client.KakaoUserClient

@Configuration
class WebConfiguration {
    private val logging = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    @Bean
    fun googleTokenCliient(
        @Value("\${social.google.tokenUrl}") baseUrl: String
    ): GoogleTokenClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GoogleTokenClient::class.java)
    }

    @Bean
    fun googleUserClient(
        @Value("\${social.google.userUrl}") baseUrl: String
    ): GoogleUserClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GoogleUserClient::class.java)
    }

    @Bean
    fun kakaoTokenClient(
        @Value("\${social.kakao.tokenUrl}") baseUrl: String
    ): KakaoTokenClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(KakaoTokenClient::class.java)
    }

    @Bean
    fun kakaoUserClient(
        @Value("\${social.kakao.userUrl}") baseUrl: String
    ): KakaoUserClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(KakaoUserClient::class.java)
    }
}