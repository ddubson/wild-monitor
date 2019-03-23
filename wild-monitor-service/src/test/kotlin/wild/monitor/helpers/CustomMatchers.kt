package wild.monitor.helpers

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Factory
import org.hamcrest.Matcher
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class IsISODateTimeCloseTo(private val expectedIsoDateTime: LocalDateTime) : BaseMatcher<String>() {
    override fun describeTo(description: Description?) {
        description?.appendText("to be within 10 seconds of ${expectedIsoDateTime.format(DateTimeFormatter.ISO_DATE_TIME)}")
    }

    override fun matches(actualIsoLocalDateTimeString: Any?): Boolean {
        // Match within 10seconds of each other
        val actualIsoLocalDateTime = LocalDateTime.parse(actualIsoLocalDateTimeString as String, DateTimeFormatter.ISO_DATE_TIME)

        val timeDifferenceInSeconds = Duration.between(actualIsoLocalDateTime, expectedIsoDateTime).toMillis() / 1000
        if(timeDifferenceInSeconds > 10) {
            return false
        }

        return true
    }

    companion object {
        @Factory
        fun isISODateTimeCloseTo(closeToValue: String): Matcher<String> {
            val expectedIsoDateTime = LocalDateTime.parse(closeToValue, DateTimeFormatter.ISO_DATE_TIME)
            return IsISODateTimeCloseTo(expectedIsoDateTime)
        }
    }
}