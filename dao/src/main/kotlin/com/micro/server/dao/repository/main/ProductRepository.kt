package com.micro.server.dao.repository.main

import com.micro.server.dao.entity.Product
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, String> {

    @Modifying
    @Query(
        """
        UPDATE product SET
            product_name = :productName,
            version = (:version + 1)
        WHERE
            product_id = :productId
            AND version = :version
    """
    )
    fun updateProductName(productName: String, productId: String, version: Int)
}
