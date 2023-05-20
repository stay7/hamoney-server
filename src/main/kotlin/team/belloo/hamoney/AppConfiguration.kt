package team.belloo.hamoney

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import team.belloo.hamoney.client.KakaoTokenClient
import team.belloo.hamoney.client.KakaoUserClient
import team.belloo.hamoney.domain.GetKakaoUser
import java.time.Clock
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Configuration
@PropertySource("classpath:conf/app-\${spring.profiles.active}.properties")
class AppConfiguration(
    @Value("\${baseUrl}") private val baseUrl: String,
) {

    @Bean
    fun defaultClock(): Clock = Clock.systemDefaultZone()

    @Bean
    fun dateTimeFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("Asia/Seoul"))

    @Bean
    fun getKakaoUser(kakaoTokenClient: KakaoTokenClient, kakaoUserClient: KakaoUserClient) =
        GetKakaoUser(kakaoTokenClient, kakaoUserClient, baseUrl)
}