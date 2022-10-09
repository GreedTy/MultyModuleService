package com.micro.server.core.exception

import com.micro.server.core.type.ResponseCode
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.TooManyRequests
import org.springframework.web.servlet.NoHandlerFoundException

/**
 * Common Exception Handler
 *
 * @author n2204442
 * @version 1.0.0
 * 작성일 2022/05/21
**/

@RestControllerAdvice
class ApiExceptionAdvisor {

    private val logger = LoggerFactory.getLogger(ApiExceptionAdvisor::class.java)

    @ExceptionHandler(value = [HttpMessageNotReadableException::class, ServiceException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestHandler(e: Exception): ResponseEntity<ResponseSet> {
        return ResponseCode.BAD_REQUEST_PARAMETER.let {
            ResponseEntity.status(400).body(ResponseSet(it.status.value(), it.detail))
        }
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandler(): ResponseEntity<ResponseSet> {
        return ResponseCode.NOT_EXIST_API.let {
            ResponseEntity.status(404).body(ResponseSet(it.status.value(), it.detail))
        }
    }

    @ExceptionHandler(TooManyRequests::class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    fun tooManyRequestHandler(): ResponseEntity<ResponseSet> {
        return ResponseCode.TOO_MANY_REQUEST.let {
            ResponseEntity.status(429).body(ResponseSet(it.status.value(), it.detail))
        }
    }
}
