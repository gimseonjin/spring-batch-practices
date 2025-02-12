package org.example.batch.job.api

import org.example.batch.listener.ApiJobExecutionListener
import org.example.batch.tasklet.ApiEndTasklet
import org.example.batch.tasklet.ApiStartTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ApiJobConfiguration {

    @Bean
    fun apiJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        apiStep1: Step,
        apiStep2: Step
    ): Job = JobBuilder("apiJob", jobRepository)
        .listener(ApiJobExecutionListener())
        .start(apiStep1)
        .next(jobStep)
        .next(apiStep2)
        .build()

    @Bean
    fun apiStep1(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        apiStartTasklet: ApiStartTasklet
    ) = StepBuilder("apiStep1", jobRepository)
        .tasklet(apiStartTasklet, transactionManager)
        .build()

    @Bean
    fun apiStep2(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        apiEndTasklet: ApiEndTasklet
    ) = StepBuilder("apiStep2", jobRepository)
        .tasklet(apiEndTasklet, transactionManager)
        .build()
}