package com.micro.server.core.util

import com.micro.server.core.misc.DF_ISO8601_UTC
import com.micro.server.core.misc.DF_STD_DATE
import com.micro.server.core.misc.DF_STD_DATETIME
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val patDate = Pair(
    Regex("^[0-9]{4}-[0-9]{2}-[0-9]{2}$"),
    DateTimeFormatter.ofPattern(DF_STD_DATE)
)

val patDateTime = Pair(
    Regex("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$"),
    DateTimeFormatter.ofPattern(DF_STD_DATETIME)
)

val patUtcDateTime = Pair(
    Regex("^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z$"),
    DateTimeFormatter.ofPattern(DF_ISO8601_UTC)
)

fun String.toLocalDateTime(): LocalDateTime {
    return when {
        this.matches(patDate.first) -> LocalDate.parse(this, patDate.second).atStartOfDay()
        this.matches(patDateTime.first) -> LocalDateTime.parse(this, patDateTime.second)
        this.matches(patUtcDateTime.first) -> LocalDateTime.parse(this, patUtcDateTime.second)
        else -> throw IllegalArgumentException(this)
    }
}
