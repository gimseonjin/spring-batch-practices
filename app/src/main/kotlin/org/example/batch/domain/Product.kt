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
)

data class ProductVo(
    var id: Long? = null,
    var name: String? = null,
    var price: Int? = null,
    var type: String? = null
)