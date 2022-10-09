package com.micro.server.batch.config.app

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock
import java.time.ZoneId

@Configuration
class AppConfig {
    @Bean
    fun clock(): Clock {
        return Clock.tickMillis(ZoneId.systemDefault())
    }
}
