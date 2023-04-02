package team.belloo.hamoney.webapi

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import team.belloo.hamoney.client.GoogleTokenClient
import team.belloo.hamoney.client.GoogleUserClient
import team.belloo.hamoney.client.KakaoTokenClient
import team.belloo.hamoney.client.KakaoUserClient

@Configuration
class WebConfiguration {

    @Bean
    fun googleTokenCliient(
        @Value("\${social.google.tokenUrl}") baseUrl: String
    ): GoogleTokenClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(GoogleTokenClient::class.java)
    }

    @Bean
    fun googleUserClient(
        @Value("\${social.google.userUrl}") baseUrl: String
    ): GoogleUserClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(GoogleUserClient::class.java)
    }

    @Bean
    fun kakaoTokenClient(
        @Value("\${social.kakao.tokenUrl}") baseUrl: String
    ): KakaoTokenClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(KakaoTokenClient::class.java)
    }

    @Bean
    fun kakaoUserClient(
        @Value("\${social.kakao.userUrl}") baseUrl: String
    ): KakaoUserClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(KakaoUserClient::class.java)
    }
}