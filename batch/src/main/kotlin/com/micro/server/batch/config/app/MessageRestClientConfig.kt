package com.micro.server.batch.config.app

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "message")
data class MessageApiConfig(
    val apiHost: String,
    val serviceId: String,
    val clientRegistrationId: String,
    val clientId: String,
    val clientSecret: String,
    val tokenUri: String
)
