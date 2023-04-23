package team.belloo.hamoney

import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.Clock
import java.time.format.DateTimeFormatter

@Configuration
class WebMvcConfiguration(
    private val clock: Clock,
    private val dateTimeFormatter: DateTimeFormatter,
    private val authenticateInterceptor: AuthenticateInterceptor
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(UserResolver())
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticateInterceptor)
    }

    @Bean
    fun registAccessLogFilter(): FilterRegistrationBean<AccessLogFilter> {
        val accessLogger = LoggerFactory.getLogger("ACCESS_LOG")
        val accessLogFilter =
            FilterRegistrationBean<AccessLogFilter>(AccessLogFilter(dateTimeFormatter, accessLogger, clock))
        accessLogFilter.addUrlPatterns("/*")
        return accessLogFilter
    }
}