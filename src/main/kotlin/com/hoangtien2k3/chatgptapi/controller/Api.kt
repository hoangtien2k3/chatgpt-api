package com.hoangtien2k3.chatgptapi.controller

import com.hoangtien2k3.chatgptapi.dto.ChatGPTRequest
import com.hoangtien2k3.chatgptapi.dto.ChatGptResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/bot")
class Api (
    @Autowired
    private val webClientBuilder: WebClient.Builder,
    @Value("\${openai.model}")
    private val model: String,
    @Value("\${openai.api.url}")
    private val apiURL: String
) {
    @GetMapping("/chat")
    @ResponseBody
    fun chat(@RequestParam("message") message: String): Mono<String> {
        val request = ChatGPTRequest(model, message)

        return webClientBuilder.build()
            .post()
            .uri(apiURL)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(ChatGptResponse::class.java)
            .map {
                it.choices[0].message.content
            }
    }
}