package br.com.froli.starwars.domains.planets.model

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.SASI
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table("planet")
data class Planet(
        @PrimaryKey
        val id: UUID?,
        @SASI(value = "name_idx", indexMode = SASI.IndexMode.CONTAINS)
        @SASI.StandardAnalyzed(normalization = SASI.Normalization.LOWERCASE)
        val name: String,
        val climate: String,
        val terrain: String,
        @Column("total_appearances")
        val totalAppearances: Int?
)