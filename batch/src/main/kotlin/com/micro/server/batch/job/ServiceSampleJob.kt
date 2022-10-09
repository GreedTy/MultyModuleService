package com.micro.server.batch.job

import com.micro.server.batch.config.batch.BatchConfig.Companion.JOB_REPOSITORY
import com.micro.server.batch.config.db.BatchDbMainConfig.Companion.TRANSACTION_MANAGER
import com.micro.server.batch.core.ExtendJob
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.PagingQueryProvider
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.core.convert.EntityRowMapper
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class ProductJob(
    @Qualifier(JOB_REPOSITORY)
    val jobRepository: JobRepository,
    @Qualifier(TRANSACTION_MANAGER)
    val transactionManager: PlatformTransactionManager,
    @Qualifier(DbMainConfig.DATA_SOURCE)
    val dataSource: DataSource,
    val productService: ProductService,
    val mappingContext: RelationalMappingContext,
    val jdbcConverter: JdbcConverter
) : ExtendJob(
    jobBuilderFactory = JobBuilderFactory(jobRepository),
    stepBuilderFactory = StepBuilderFactory(jobRepository, transactionManager)
) {

    companion object {
        const val JOB_NAME = "ServiceJob"
        const val STEP_NAME = "ServiceStep"
        const val READER_NAME = "${STEP_NAME}Reader"
        const val WRITER_NAME = "${STEP_NAME}Writer"
        const val CHUNK_SIZE = 1
    }

    @Suppress("UNCHECKED_CAST")
    private val rowMapper by lazy {
        EntityRowMapper(
            mappingContext.getRequiredPersistentEntity(Product::class.java)
                as RelationalPersistentEntity<Product>,
            jdbcConverter
        )
    }

    @Bean(JOB_NAME)
    fun job(
        @Qualifier(STEP_NAME) step: Step
    ): Job {
        return jobBuilderFactory.get(JOB_NAME)
            .incrementer(RunIdIncrementer())
            .start(step)
            .build()
    }

    @Bean(STEP_NAME)
    fun step(
        @Qualifier(READER_NAME) reader: ItemReader<Product>,
        @Qualifier(WRITER_NAME) writer: ItemWriter<Product>
    ): Step = stepBuilderFactory.get(STEP_NAME)
        .chunk<Product, Product>(CHUNK_SIZE)
        .reader(reader)
        .writer(writer)
        .build()

    @StepScope
    @Bean(READER_NAME)
    fun reader(): JdbcPagingItemReader<Product> {
        val map = HashMap<String, Any>()
        return JdbcPagingItemReaderBuilder<Product>()
            .name(READER_NAME)
            .dataSource(dataSource)
            .queryProvider(queryProvider())
            .parameterValues(map)
            .rowMapper(rowMapper)
            // .pageSize(10)
            // .fetchSize(10)
            // .currentItemCount(0)
            // .maxItemCount(1)
            .build()
    }

    @Bean(WRITER_NAME)
    fun writer(): ItemWriter<Product> = ItemWriter { list ->
        list.map { product ->
            productService.findByProductId(product.productId)
        }
    }

    fun queryProvider(): PagingQueryProvider {
        return SqlPagingQueryProviderFactoryBean().apply {
            this.setDataSource(dataSource)
            this.setSelectClause("*")
            this.setFromClause("product")
            this.setWhereClause(
                """
                1 = 1
                AND created_at > '2022-7-31T00:00:00.000Z'
                AND created_at < '2022-8-02T00:00:00.000Z'
                """.trimIndent()
            )
            this.setSortKey("created_at")
        }.`object`
    }
}
