package br.com.froli.starwars.application.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@Configuration
@EnableCassandraRepositories
class CassandraConfig : AbstractCassandraConfiguration() {

    @Value("\${cassandra.contactpoints}")
    private val contactPoints: String? = null

    @Value("\${cassandra.port}")
    private val port: Int = 0

    @Value("\${cassandra.keyspace}")
    private val keyspace: String? = null

    @Value("\${cassandra.basepackages}")
    private val basePackages: String? = null

    override fun getKeyspaceName(): String {
        return keyspace!!
    }

    override fun getContactPoints(): String {
        return contactPoints!!
    }

    override fun getPort(): Int {
        return port
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }

    override fun getEntityBasePackages(): Array<String> {
        return arrayOf<String>(basePackages!!)
    }

    override fun getKeyspaceCreations(): List<CreateKeyspaceSpecification> {
        val specification = CreateKeyspaceSpecification.createKeyspace(keyspace!!)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication()
        return listOf(specification)
    }

    /*override fun getKeyspaceDrops(): List<DropKeyspaceSpecification> {
        return listOf(DropKeyspaceSpecification.dropKeyspace(keyspace!!))
    }*/

    override fun getMetricsEnabled(): Boolean {
        return false
    }
}