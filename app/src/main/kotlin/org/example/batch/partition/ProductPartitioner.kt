package org.example.batch.partition

import org.springframework.batch.core.partition.support.Partitioner
import org.springframework.batch.item.ExecutionContext
import javax.sql.DataSource

class ProductPartitioner(
    private val dataSource: DataSource
): Partitioner {
    override fun partition(gridSize: Int): MutableMap<String, ExecutionContext> {
        TODO("Not yet implemented")
    }
}