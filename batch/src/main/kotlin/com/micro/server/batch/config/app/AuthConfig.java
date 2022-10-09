package com.micro.server.batch.config.app;

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.*
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AuthConfig {

    @Bean("authManager")
    fun authorizedClientManager(
            @Qualifier("serviceClientRegistrationRepository")
            clientRegistrationRepository: ClientRegistrationRepository,
            @Qualifier("serviceOAuth2AuthorizedClientService")
            authorizedClientService: OAuth2AuthorizedClientService
    ): OAuth2AuthorizedClientManager {
        val authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build()
        val authorizedClientManager = AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService)
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
        return authorizedClientManager
    }

    @Bean("messageClientRegistration")
    fun clientRegistration(messageConfig: MessageApiConfig): ClientRegistration {
        return ClientRegistration.withRegistrationId(messageConfig.clientRegistrationId)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientId(messageConfig.clientId)
                .clientSecret(messageConfig.clientSecret)
                .tokenUri(messageConfig.tokenUri)
                .build()
    }

    @Bean("serviceClientRegistrationRepository")
    fun clientRegistrationRepository(
            @Qualifier("messageClientRegistration") messageClientRegistration: ClientRegistration
            ): ClientRegistrationRepository {
        return InMemoryClientRegistrationRepository(listOf(messageClientRegistration)
    }

    @Bean("serviceOAuth2AuthorizedClientService")
    fun oAuth2AuthorizedClientService(
            @Qualifier("serviceClientRegistrationRepository") clientRegistrationRepository: ClientRegistrationRepository
    ): OAuth2AuthorizedClientService {
        return InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository)
    }

    @Bean("accountClient")
    fun getAccountWebClient(
            @Qualifier("authManager") authorizedClientManager: OAuth2AuthorizedClientManager,
            @Qualifier("messageClientRegistration") messageClientRegistration: ClientRegistration
    ): WebClient {
        val oauth2Client = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
        oauth2Client.setDefaultClientRegistrationId(messageClientRegistration.registrationId)
        return WebClient.builder().apply(oauth2Client.oauth2Configuration()).build()
    }
}