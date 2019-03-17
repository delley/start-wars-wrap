package br.com.froli.starwars.domains.planets.router

import br.com.froli.starwars.domains.planets.handler.PlanetHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.router

@Component
class PlanetRouter(private val planetHandler: PlanetHandler) {

    @Bean
    fun planetRouts(): RouterFunction<*> = router {
        "/planet".nest {
            GET("/{id}", planetHandler::find)
            GET("/", planetHandler::search)
            POST("/", planetHandler::create)
            DELETE("/{id}", planetHandler::remove)
        }
        "/swapi".nest {
            GET("/{page}", planetHandler::seachInSWAPI)
        }
    }
}