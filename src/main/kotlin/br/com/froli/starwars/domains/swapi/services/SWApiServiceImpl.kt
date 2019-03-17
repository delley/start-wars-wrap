package br.com.froli.starwars.domains.swapi.services

import br.com.froli.starwars.domains.swapi.model.SWPlanetResponse
import br.com.froli.starwars.domains.planets.model.Planet
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class SWApiServiceImpl : SWApiService {

    override fun searchInSWAPI(pageIndex: Int): Mono<SWPlanetResponse> {
        val client = WebClient.create("https://swapi.co/api/planets")

        return client.get()
                .uri("/?page={pageIndex}", pageIndex)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SWPlanetResponse::class.java)
                .map {
                    val next = it.next?.replace("https://swapi.co/api/planets/?page=","")
                    val previous = it.previous?.replace("https://swapi.co/api/planets/?page=","")
                    SWPlanetResponse(it.count, next, previous, it.results)
                }
    }

    override fun findByName(name: String): Mono<List<Planet>> {

        val client = WebClient.create("https://swapi.co/api/planets")

        return client.get()
                .uri("/?search={name}", name)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SWPlanetResponse::class.java)
                .map {
                    it.results.map { p ->
                        Planet(null, p.name, p.climate, p.terrain, p.films.size)
                    }
                }

    }

}