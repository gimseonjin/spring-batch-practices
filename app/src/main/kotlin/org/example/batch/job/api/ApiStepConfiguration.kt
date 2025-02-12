package org.example.batch.job.api

import org.example.batch.domain.ProductVo
import org.example.batch.partition.ProductPartitioner
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class ApiStepConfiguration {

    private val chunkSize = 10

    @Bean
    fun apiMasterStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
    ) = StepBuilder("apiMasterStep", jobRepository)
        .partitioner(apiSlaveStep().name, partitioner())
        .step(apiSlaveStep())
        .gridSize(4)
        .taskExecutor(taskExecutor())
        .build()

    @Bean
    fun apiSlaveStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
    ) = StepBuilder("apiSlaveStep", jobRepository)
        .chunk<ProductVo, ProductVo>(chunkSize, transactionManager)
        .reader(apiItemReader())
        .processor(apiItemProcessor())
        .writer(apiItemWriter())
        .build()

    @Bean
    fun partitioner(
        dataSource: DataSource
    ): ProductPartitioner = ProductPartitioner(dataSource)
}