package org.example.batch.job.api

import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ApiJobChildConfiguration(
    private val jobLauncher: JobLauncher
) {

    @Bean
    fun jobStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        childJob: Job
    ) = StepBuilder("jobStep", jobRepository)
        .job(childJob)
        .launcher(jobLauncher)
        .build()

    @Bean
    fun childJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ) = JobBuilder("childJob", jobRepository)
        .start(childStep1())
        .build()
}