package com.micro.server.batch.config.batch

import com.micro.server.batch.config.db.BatchDbMainConfig.Companion.DATA_SOURCE
import com.micro.server.batch.config.db.BatchDbMainConfig.Companion.TRANSACTION_MANAGER
import com.micro.server.core.util.toLocalDateTime
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class BatchConfig : DefaultBatchConfigurer() {

    @Autowired
    override fun setDataSource(@Qualifier(DATA_SOURCE) dataSource: DataSource) {
        super.setDataSource(dataSource)
    }

    @Bean(JOB_REPOSITORY)
    fun jobRepository(
        @Qualifier(TRANSACTION_MANAGER)
        batchMainTransactionManager: PlatformTransactionManager,
        @Qualifier(DATA_SOURCE)
        dataSource: DataSource
    ): JobRepository {
        val factory = JobRepositoryFactoryBean()
        factory.setDataSource(dataSource)
        factory.transactionManager = batchMainTransactionManager
        factory.setIsolationLevelForCreate("ISOLATION_DEFAULT")
        return factory.getObject()
    }

    companion object {
        private const val DB = "batch"
        const val JOB_REPOSITORY = "${DB}JobRepository"
    }
}

object JobParameterKey {
    const val JOB_PARAMS = "#{jobParameters}"
    const val CRITERIA_DATE_TIME_START = "job.param.criteriaDateTimeStart"
    const val CRITERIA_DATE_TIME_END = "job.param.criteriaDateTimeEnd"
}

val originDateTime: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

typealias JobParam = Map<String, Any>

/**
 * 시간 체계는 다음과 같다.
 *
 * 배치를 실행하는 서버 환경은 모두 UTC 타임존으로 설정되어 있다.
 * 사용되는 시간 포맷은 UTC 포맷이지만 실질적인 실행은 한국시간 기준으로 배치를 실행한다.
 *
 * 배치 실행시 criteriaDateTimeStart 와 criteriaDateTimeEnd 을 별도로 지정하지 않았다면 기본 값은 다음과 같다.
 *
 * * criteriaDateTimeStart = 한국시간 기준으로 배치를 실행한 날 00 시의 하루전 UTC 타임
 * * criteriaDateTimeEnd = 한국시간 기준으로 배치를 실행한 날 00 시의 UTC 타임
 *
 * 예를 들어, 한국시간 기준으로 7월 6일 03:00 분에 배치를 실행했다면
 *
 * * criteriaDateTimeStart = 7월 4일 15 시 00 분
 * * criteriaDateTimeEnd = 7월 5일 15 시 00 분
 *
 * 이 된다.
 */
val JobParam.criteriaDateTimeStart: LocalDateTime
    get() = (this[JobParameterKey.CRITERIA_DATE_TIME_START] as String?)?.toLocalDateTime()
        ?: LocalDateTime.ofInstant(
            originDateTime.truncatedTo(ChronoUnit.DAYS).minusDays(1).toInstant(),
            ZoneOffset.UTC
        )

val JobParam.criteriaDateTimeEnd: LocalDateTime
    get() = (this[JobParameterKey.CRITERIA_DATE_TIME_END] as String?)?.toLocalDateTime()
        ?: LocalDateTime.ofInstant(
            originDateTime.truncatedTo(ChronoUnit.DAYS).toInstant(),
            ZoneOffset.UTC
        )
