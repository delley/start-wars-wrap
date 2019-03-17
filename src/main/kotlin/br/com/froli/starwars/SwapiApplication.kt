package br.com.froli.starwars

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SwapiApplication

fun main(args: Array<String>) {
    runApplication<SwapiApplication>(*args)
}