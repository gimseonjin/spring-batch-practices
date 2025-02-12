package org.example.batch.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Product(
    @Id
    var id: Long,
    var name: String,
    var price: Int,
    var type: String
) {
    companion object {
        fun from(productVo: ProductVo): Product {
            return Product(
                id = productVo.id!!,
                name = productVo.name!!,
                price = productVo.price!!,
                type = productVo.type!!
            )
        }
    }
}

data class ProductVo(
    var id: Long? = null,
    var name: String? = null,
    var price: Int? = null,
    var type: String? = null
)