package team.belloo.hamoney

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:conf/app_config-\${spring.profiles.active}.properties")
class AppConfiguration {
}