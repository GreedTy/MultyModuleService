package com.micro.server.domain.event.listener

import com.micro.server.domain.event.domain.EventDomainBuilder
import com.micro.server.domain.service.ProductService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ServiceEventListener (
    val productService: ProductService
) {

    @Async
    @TransactionalEventListener
    fun handleSendServiceEvent(productInfo: EventDomainBuilder.ProductInfo) {
        TODO("Implementation")
    }

    @Async
    @TransactionalEventListener
    fun handleOfferServiceEvent(categoryInfo: EventDomainBuilder.CategoryInfo) {
        TODO("Implementation")
    }
}
