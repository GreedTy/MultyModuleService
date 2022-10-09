package com.micro.server.domain.event.publisher

import com.micro.server.domain.event.domain.EventDomainBuilder
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ServiceEventPublisher (
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Async
    fun productEvent(productEvent: EventDomainBuilder.ProductInfo) {
        applicationEventPublisher.publishEvent(productEvent)
    }

    @Async
    fun productEvent(categoryEvent: EventDomainBuilder.CategoryInfo) {
        applicationEventPublisher.publishEvent(categoryEvent)
    }
}
