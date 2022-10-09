package com.micro.server.batch

import com.micro.server.batch.config.app.MessageApiConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(
    value = [
        MessageApiConfig::class
    ]
)
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}
