package br.com.froli.starwars.domains.planets.services

import br.com.froli.starwars.SwapiApplication
import br.com.froli.starwars.domains.planets.model.Planet
import br.com.froli.starwars.domains.planets.repository.PlanetRepository
import br.com.froli.starwars.domains.planets.services.PlanetService
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [SwapiApplication::class])
class PlanetServiceTest {

    @Autowired
    lateinit var planetService: PlanetService

    @MockBean
    private val planetRepository: PlanetRepository? = null

    private val id = UUID.fromString("a2778be9-9b58-4a7b-b9e5-a9ddc558a8fa")

    @Before
    @Throws(Exception::class)
    fun setUp(){
        val p = planet()
        When calling planetRepository?.findById(p.id!!) `it returns` Mono.just(p)
        When calling planetRepository?.findByName(BDDMockito.anyString()) `it returns` Flux.just(p)
        When calling planetRepository?.findAll() `it returns` Flux.just(p, p, p)
    }

    @Test
    fun `we should get a planet with a valid id`() {
        val planet = planetService.findBy(id).block()
        planet.`should not be null`()
        planet?.name `should be` "Tatooine"
    }

    @Test
    fun `we should get a planet with a valid name`() {
        val planet = planetService.searchBy(planet().name).blockFirst()
        planet.`should not be null`()
        planet?.name `should be` "Tatooine"
    }

    @Test
    fun `we should get all planets`() {
        val planets = planetService.searchBy("").collectList().block()
        planets.`should not be null`()
        planets!!.size `should be equal to` 3
    }

    private fun planet(): Planet = Planet(id, "Tatooine", "arid", "desert", 5)
}