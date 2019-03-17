package br.com.froli.starwars.domains.planets.services

import br.com.froli.starwars.domains.planets.model.Planet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface PlanetService {

    fun findBy(id: UUID): Mono<Planet>

    fun searchBy(nameFilter: String): Flux<Planet>

    fun create(planet: Mono<Planet>): Mono<Planet>

    fun remove(id: UUID): Mono<Void>

}