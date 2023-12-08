package com.hoangtien2k3.chatgptapi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
@RestController
class CustomExceptionHandler(private val webClientBuilder: WebClient.Builder) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(HttpClientErrorException::class, HttpServerErrorException::class)
    fun handleOpenAIException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        if (ex is HttpClientErrorException) {
            if (ex.statusCode == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You didn't provide an API key. Provide your API key.")
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.")
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity.status(ex.statusCode).body(ex.reason)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception): String {
        return "An unexpected error occurred: ${ex.message}"
    }

}