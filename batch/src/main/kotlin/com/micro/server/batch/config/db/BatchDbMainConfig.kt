package com.micro.server.batch.config.db

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories(
    jdbcOperationsRef = BatchDbMainConfig.NAMED_PARAM_JDBC_OPRS,
    basePackages = ["com.micro.server.batch"],
    transactionManagerRef = BatchDbMainConfig.TRANSACTION_MANAGER,
    dataAccessStrategyRef = BatchDbMainConfig.DATA_ACCESS_STRATEGY
)
class BatchDbMainConfig : AbstractJdbcConfiguration() {

    @Bean(DATA_SOURCE)
    @ConfigurationProperties(prefix = "$DB.main.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Bean(NAMED_PARAM_JDBC_OPRS)
    fun namedParameterJdbcOperations(@Qualifier(DATA_SOURCE) dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean(TRANSACTION_MANAGER)
    fun transactionManager(@Qualifier(DATA_SOURCE) dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean(DATA_ACCESS_STRATEGY)
    override fun dataAccessStrategyBean(
        @Qualifier(NAMED_PARAM_JDBC_OPRS) operations: NamedParameterJdbcOperations,
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
        private const val DB = "batch"
        const val DATA_SOURCE = "${DB}MainDataSource"
        const val NAMED_PARAM_JDBC_OPRS = "${DB}MainNamedParameterJdbcOperations"
        const val TRANSACTION_MANAGER = "${DB}MainTransactionManager"
        const val DATA_ACCESS_STRATEGY = "${DB}MainDataAccessStrategy"
    }
}
