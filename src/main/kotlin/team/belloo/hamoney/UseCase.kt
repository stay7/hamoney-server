package team.belloo.hamoney

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class UseCase