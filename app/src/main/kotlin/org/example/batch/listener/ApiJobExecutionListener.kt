package org.example.batch.listener

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class ApiJobExecutionListener: JobExecutionListener {

    override fun beforeJob(jobExecution: JobExecution) {
        println("API Job Started")
    }

    override fun afterJob(jobExecution: JobExecution) {
        println("API Job Ended")
    }
}