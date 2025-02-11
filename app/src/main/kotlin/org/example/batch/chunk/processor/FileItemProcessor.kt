package org.example.batch.chunk.processor

import org.example.batch.domain.Product
import org.example.batch.domain.ProductVo
import org.springframework.batch.item.ItemProcessor

class FileItemProcessor: ItemProcessor<ProductVo, Product> {
    override fun process(item: ProductVo): Product? {
        TODO("Not yet implemented")
    }
}