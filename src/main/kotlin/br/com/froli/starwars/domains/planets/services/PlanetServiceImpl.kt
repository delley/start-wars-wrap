package br.com.froli.starwars.domains.planets.services

import br.com.froli.starwars.common.DuplicatePlanetException
import br.com.froli.starwars.common.PlanetSWAPiNotFoundException
import br.com.froli.starwars.common.PlanetSWApiAmbiguous
import br.com.froli.starwars.common.PlanetSWApiNameNotMatch
import br.com.froli.starwars.domains.planets.model.Planet
import br.com.froli.starwars.domains.planets.repository.PlanetRepository
import br.com.froli.starwars.domains.swapi.services.SWApiService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*

@Service
class PlanetServiceImpl(val planetRepository: PlanetRepository, val swApiService: SWApiService) : PlanetService {

    override fun findBy(id: UUID) = planetRepository.findById(id)

    override fun searchBy(nameFilter: String): Flux<Planet> {
        return if (nameFilter == "") {
            planetRepository.findAll()
                    .delayElements(Duration.ofMillis(500)) // delay each element with 500 ms to see that Server-Sent events are streamed
        } else {
            planetRepository.findByName("%$nameFilter%")
                    .delayElements(Duration.ofMillis(500)) // delay each element with 500 ms to see that Server-Sent events are streamed
        }
    }

    override fun create(planetMono: Mono<Planet>): Mono<Planet> {

        return planetMono.flatMap { m ->
            swApiService.findByName(m.name).flatMap { planets ->
                if (planets.isEmpty())
                    throw PlanetSWAPiNotFoundException("the '${m.name}' planet was not found in SWApi")

                if (planets.size > 1)
                    throw PlanetSWApiAmbiguous("the '${m.name}' planet is ambiguous in SWApi")

                if (!m.name.equals(planets[0].name, true))
                    throw PlanetSWApiNameNotMatch("The name of the new planet ('${m.name}') is not exactly like the name of the planet SWApi ('${planets[0].name}')")

                planetRepository.findByName(m.name).count().map { c ->
                    if (c > 0L)
                        throw DuplicatePlanetException("The '${m.name}' planet already exists in the database")

                }.flatMap {
                    planetRepository.save(Planet(UUID.randomUUID(), m.name.capitalize(), m.climate, m.terrain, planets[0].totalAppearances))
                }
            }
        }

    }

    override fun remove(id: UUID) = planetRepository.deleteById(id)

}
