package wild.monitor.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private fun dateTimeNow(): LocalDateTime = LocalDateTime.now()

fun dateTimeRightNow(): String = dateTimeNow().format(DateTimeFormatter.ISO_DATE_TIME)

fun anHourFromNow(): String = dateTimeNow().plusHours(1).format(DateTimeFormatter.ISO_DATE_TIME)