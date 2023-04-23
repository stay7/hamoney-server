package team.belloo.hamoney

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.time.Clock
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Configuration
@PropertySource("classpath:conf/app_config-\${spring.profiles.active}.properties")
class AppConfiguration {

    @Bean
    fun defaultClock(): Clock = Clock.systemDefaultZone()

    @Bean
    fun dateTimeFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("Asia/Seoul"))
}