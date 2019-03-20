package wild.monitor.models

import java.util.*

data class Job(val id: UUID,
               val status: JobStatus,
               val projectKey: String) {
}