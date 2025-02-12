package org.example.batch.job.file

import jakarta.persistence.EntityManagerFactory
import org.example.batch.chunk.processor.FileItemProcessor
import org.example.batch.domain.Product
import org.example.batch.domain.ProductVo
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder

import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class FileJobConfiguration {

    @Bean
    fun fileJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        fileStep: Step
    ): Job = JobBuilder("fileJob", jobRepository)
        .start(fileStep)
        .build()

    @Bean
    fun fileStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        fileItemReader: FlatFileItemReader<ProductVo>,
        fileItemProcessor: FileItemProcessor,
        fileItemWriter: ItemWriter<Product>
    ): Step = StepBuilder("fileStep", jobRepository)
        .chunk<ProductVo, Product>(100, transactionManager)
        .reader(fileItemReader)
        .processor(fileItemProcessor)
        .writer(fileItemWriter)
        .build()

    @Bean
    @JobScope
    fun fileItemReader(
        @Value("#{jobParameters['requestDate']}") requestDate: String
    ): FlatFileItemReader<ProductVo> = FlatFileItemReaderBuilder<ProductVo>()
        .name("fileItemReader")
        .resource(ClassPathResource("product_" + requestDate  + ".csv"))
        .fieldSetMapper(BeanWrapperFieldSetMapper())
        .targetType(ProductVo::class.java)
        .linesToSkip(1)
        .delimited().delimiter(",")
        .names("id", "name", "price", "type")
        .build()

    @Bean
    fun fileItemProcessor(): FileItemProcessor = FileItemProcessor()

    @Bean
    fun fileItemWriter(
        entityManagerFactory: EntityManagerFactory
    ): ItemWriter<Product> = JpaItemWriterBuilder<Product>()
        .entityManagerFactory(entityManagerFactory)
        .build()
}