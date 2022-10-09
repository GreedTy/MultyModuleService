package com.micro.server.core.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.micro.server.core.type.ResponseCode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.status = HttpStatus.UNAUTHORIZED.value()
        response?.outputStream.use {
            val objectMapper = ObjectMapper()
            ResponseCode.UNAUTHORIZATION.also { response ->
                objectMapper.writeValue(it, ResponseSet(response.status.value(), response.detail))
            }
            it?.flush()
        }
    }
}
