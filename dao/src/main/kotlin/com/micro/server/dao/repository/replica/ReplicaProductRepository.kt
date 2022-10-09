package com.micro.server.dao.repository.replica

import com.micro.server.dao.entity.Product
import org.springframework.data.repository.CrudRepository

interface ReplicaProductRepository : CrudRepository<Product, String> {

    fun findByProductId(productId: String): Product?
}
