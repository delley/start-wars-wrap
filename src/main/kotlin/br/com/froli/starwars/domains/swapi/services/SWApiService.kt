package br.com.froli.starwars.domains.swapi.services

import br.com.froli.starwars.domains.swapi.model.SWPlanetResponse
import br.com.froli.starwars.domains.planets.model.Planet
import reactor.core.publisher.Mono

interface SWApiService {
    fun searchInSWAPI(pageIndex: Int): Mono<SWPlanetResponse>

    fun findByName(name: String): Mono<List<Planet>>
}