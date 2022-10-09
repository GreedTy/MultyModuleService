package com.micro.server.dao.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jdbc.core.convert.DataAccessStrategy
import org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.core.convert.SqlGeneratorSource
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionManager
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories(
    jdbcOperationsRef = DbMainConfig.NAMED_PARAM_JDBC_OPERATIONS,
    basePackages = ["com.micro.server.dao.repository.main"],
    transactionManagerRef = DbMainConfig.TRANSACTION_MANAGER,
    dataAccessStrategyRef = DbMainConfig.DATA_ACCESS_STRATEGY
)
class DbMainConfig : AbstractJdbcConfiguration() {

    @Primary
    @Bean(DATA_SOURCE)
    @ConfigurationProperties(prefix = "$DB.main.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Primary
    @Bean(NAMED_PARAM_JDBC_OPERATIONS)
    fun namedParameterJdbcOperations(@Qualifier(DATA_SOURCE) dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Primary
    @Bean(TRANSACTION_MANAGER)
    fun transactionManager(@Qualifier(DATA_SOURCE) dataSource: DataSource): TransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Primary
    @Bean(DATA_ACCESS_STRATEGY)
    override fun dataAccessStrategyBean(
        @Qualifier(NAMED_PARAM_JDBC_OPERATIONS) operations: NamedParameterJdbcOperations,
        jdbcConverter: JdbcConverter,
        context: JdbcMappingContext,
        dialect: Dialect
    ): DataAccessStrategy {
        return DefaultDataAccessStrategy(
            SqlGeneratorSource(context, jdbcConverter, dialect), context,
            jdbcConverter, operations
        )
    }

    companion object {
        private const val DB = "micro"
        const val DATA_SOURCE = "${DB}MainDataSource"
        const val NAMED_PARAM_JDBC_OPERATIONS = "${DB}MainNamedParameterJdbcOpertaions"
        const val TRANSACTION_MANAGER = "${DB}MainTransactionManager"
        const val DATA_ACCESS_STRATEGY = "${DB}MainAccessStrategy"
    }
}

@Configuration
@EnableJdbcRepositories(
    jdbcOperationsRef = DbReplicaConfig.NAMED_PARAM_JDBC_OPERATIONS,
    basePackages = ["com.micro.server.dao.repository.replica"],
    transactionManagerRef = DbReplicaConfig.TRANSACTION_MANAGER,
    dataAccessStrategyRef = DbReplicaConfig.DATA_ACCESS_STRATEGY
)
class DbReplicaConfig : AbstractJdbcConfiguration() {

    @Bean(DATA_SOURCE)
    @ConfigurationProperties(prefix = "$DB.replica.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Bean(NAMED_PARAM_JDBC_OPERATIONS)
    fun namedParameterJdbcOperations(@Qualifier(DATA_SOURCE) dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean(TRANSACTION_MANAGER)
    fun transactionManager(@Qualifier(DATA_SOURCE) dataSource: DataSource): TransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean(DATA_ACCESS_STRATEGY)
    override fun dataAccessStrategyBean(
        @Qualifier(NAMED_PARAM_JDBC_OPERATIONS) operations: NamedParameterJdbcOperations,
        jdbcConverter: JdbcConverter,
        context: JdbcMappingContext,
        dialect: Dialect
    ): DataAccessStrategy {
        return DefaultDataAccessStrategy(
            SqlGeneratorSource(context, jdbcConverter, dialect), context,
            jdbcConverter, operations
        )
    }

    companion object {
        private const val DB = "micro"
        const val DATA_SOURCE = "${DB}ReplicaDataSource"
        const val NAMED_PARAM_JDBC_OPERATIONS = "${DB}ReplicaNamedParameterJdbcOperations"
        const val TRANSACTION_MANAGER = "${DB}ReplicaTransactionManager"
        const val DATA_ACCESS_STRATEGY = "${DB}ReplicaDataAccessStrategy"
    }
}
