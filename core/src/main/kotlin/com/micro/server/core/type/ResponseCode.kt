package com.micro.server.core.type

import org.springframework.http.HttpStatus

enum class ResponseCode(val status: HttpStatus, val detail: String) {
    SUCCESS(HttpStatus.OK, "Success."),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal service error."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden Access"),
    UNAUTHORIZATION(HttpStatus.UNAUTHORIZED, "UnAuthorization Access"),
    BAD_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "Bad request parameter."),
    MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "Check Request URI, Header, Body"),
    NOT_EXIST_API(HttpStatus.BAD_REQUEST, "Not found API"),
    TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS, "Too many Request"),
    FILE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "File Upload Process error."),
    NOT_EXIST_DATA(HttpStatus.BAD_REQUEST, "The data is not exist")
}
