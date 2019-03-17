package br.com.froli.starwars.domains.planets.repository

import br.com.froli.starwars.domains.planets.model.Planet
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*

@Repository
interface PlanetRepository : ReactiveCassandraRepository<Planet, UUID> {

    @Query("SELECT * FROM planet WHERE name LIKE :name")
    fun findByName(@Param("name") name: String): Flux<Planet>

}