package com.hoangtien2k3.chatgptapi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class OpenAIConfig {
    @Value("\${openai.api.key}")
    lateinit var openaiApiKey: String

    @Value("\${openai.api.url}")
    lateinit var openApiUrl: String

    @Bean
    fun webClientBuilder(): WebClient.Builder {
        val httpClient = HttpClient.create()
            .baseUrl(openApiUrl)
            .headers { headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer $openaiApiKey") }

        val connector = ReactorClientHttpConnector(httpClient)
        return WebClient.builder().clientConnector(connector)
    }
}