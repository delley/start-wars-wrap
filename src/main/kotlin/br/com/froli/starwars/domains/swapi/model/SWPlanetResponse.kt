package br.com.froli.starwars.domains.swapi.model

import br.com.froli.starwars.domains.swapi.model.SWPlanet
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SWPlanetResponse(
        val count : Int,
        val next : String?,
        val previous : String?,
        var results: List<SWPlanet>
)