package br.com.froli.starwars.domains.planets.handler

import br.com.froli.starwars.common.DuplicatePlanetException
import br.com.froli.starwars.common.ErrorResponse
import br.com.froli.starwars.domains.swapi.model.SWPlanetResponse
import br.com.froli.starwars.domains.planets.model.Planet
import br.com.froli.starwars.domains.planets.services.PlanetService
import br.com.froli.starwars.domains.swapi.services.SWApiService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.onErrorResume
import java.net.URI
import java.util.*

@Component
class PlanetHandler(val planetService: PlanetService, val swapiService: SWApiService) {

    fun find(serverRequest: ServerRequest) =
            planetService.findBy(UUID.fromString(serverRequest.pathVariable("id")))
                    .flatMap {
                        ok().contentType(MediaType.TEXT_EVENT_STREAM).body(fromObject(it))
                    }
                    .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build())

    fun search(serverRequest: ServerRequest) =
            ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(planetService.searchBy(serverRequest.queryParam("nameFilter").orElse("")), Planet::class.java)

    fun create(serverRequest: ServerRequest) =
            planetService.create(serverRequest.bodyToMono())
                    .flatMap {
                        ServerResponse.created(URI.create("/planet/${it.id}")).build()
                    }.onErrorResume(DuplicatePlanetException::class) {
                        ServerResponse.status(HttpStatus.CONFLICT).body(fromObject(ErrorResponse("error creating planet", it.message
                                ?: "error")))
                    }.onErrorResume(Exception::class) {
                        badRequest().body(fromObject(ErrorResponse("error creating planet", it.message
                                ?: "error")))
                    }

    fun remove(serverRequest: ServerRequest) =
            planetService.findBy(UUID.fromString(serverRequest.pathVariable("id")))
                    .flatMap {
                        planetService.remove(it.id!!).then(ServerResponse.noContent().build())
                    }.switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build())

    fun seachInSWAPI(serverRequest: ServerRequest) =
            ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(swapiService.searchInSWAPI(serverRequest.pathVariable("page").toInt()), SWPlanetResponse::class.java)
}