package br.com.froli.starwars.domains.planets.repository

import br.com.froli.starwars.SwapiApplication
import br.com.froli.starwars.domains.planets.model.Planet
import br.com.froli.starwars.domains.planets.repository.PlanetRepository
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [SwapiApplication::class])
class PlanetRepositoryTest {

    @Autowired
    private val planetRepository: PlanetRepository? = null

    private val id = UUID.fromString("a2778be9-9b58-4a7b-b9e5-a9ddc558a8fa")

    @Test
    fun repositoryCrudOperations() {
        val sample = samplePlanet()
        planetRepository?.save(sample)?.block()
        val savedPlanet = planetRepository?.findById(sample.id!!)
        Assertions.assertThat(sample).isEqualTo(savedPlanet!!.block())
        val searchedPlanet = planetRepository?.findByName(sample.name)
        Assertions.assertThat(sample).isEqualTo(searchedPlanet!!.blockFirst())
        planetRepository?.deleteById(sample.id!!)?.block()
        val planet = planetRepository?.findById(sample.id!!)?.block()
        Assertions.assertThat(planet).isNull()

    }

    private fun samplePlanet() = Planet(id, "Tatooine", "arid", "desert", 5)

}