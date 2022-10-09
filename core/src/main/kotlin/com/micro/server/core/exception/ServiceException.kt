package com.micro.server.core.exception

import com.micro.server.core.type.ResponseCode

class ServiceException(responseCode: ResponseCode) : RuntimeException() {
    val responseCode = responseCode
}
