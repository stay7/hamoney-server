package team.belloo.hamoney

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HamoneyServerApplication

fun main(args: Array<String>) {
	runApplication<HamoneyServerApplication>(*args)
}
