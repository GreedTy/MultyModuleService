package com.micro.server.dao.entity

import com.micro.server.dao.core.AbstractPersistable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Product(

    @Id
    @Column("product_id")
    val productId: String,

    @Column("product_name")
    val productName: String,

    @Column("created_at")
    @CreatedDate
    val createdAt: LocalDateTime,

    @Column("updated_at")
    @LastModifiedDate
    val updatedAt: LocalDateTime?,

    @Column
    @Version
    val version: Int

) : AbstractPersistable<String, Product>() {

    override fun getId(): String {
        return id
    }

    companion object
}
