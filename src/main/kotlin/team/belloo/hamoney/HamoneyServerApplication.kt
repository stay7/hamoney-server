package team.belloo.hamoney

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class HamoneyServerApplication

fun main(args: Array<String>) {
	runApplication<HamoneyServerApplication>(*args)
}