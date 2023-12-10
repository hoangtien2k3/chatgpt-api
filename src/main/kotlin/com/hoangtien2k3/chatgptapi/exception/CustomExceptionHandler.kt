package com.hoangtien2k3.chatgptapi.exception

import org.springframework.beans.factory.annotation.Autowired
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
class CustomExceptionHandler @Autowired constructor(
    private val webClientBuilder: WebClient.Builder
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(HttpClientErrorException.Unauthorized::class)
    fun handleUnauthorizedException(ex: HttpClientErrorException.Unauthorized): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You didn't provide an API key. Provide your API key.")
    }

    @ExceptionHandler(value = [HttpClientErrorException::class, HttpServerErrorException::class])
    fun handleOpenAIException(ex: HttpClientErrorException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.")
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): ResponseEntity<String> {
        return ResponseEntity.status(ex.statusCode).body(ex.reason)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception): String {
        return "An unexpected error occurred: ${ex.message}"
    }
}
