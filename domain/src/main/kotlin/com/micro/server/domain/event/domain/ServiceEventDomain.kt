package com.micro.server.domain.event.domain

import org.springframework.context.ApplicationEvent


sealed interface EventDomainBuilder {
    data class ProductInfo(val source: ApplicationEvent, val productId: String, val productName: String): EventDomainBuilder
    data class CategoryInfo(val source: ApplicationEvent, val categoryId: String, val categoryName: String)
}
