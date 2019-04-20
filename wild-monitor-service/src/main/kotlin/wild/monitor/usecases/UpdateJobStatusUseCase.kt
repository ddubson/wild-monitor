package wild.monitor.usecases

import wild.monitor.models.Job
import wild.monitor.models.JobStatus

interface UpdateJobStatusUseCase {
    fun updateJobStatus(jobId: String, newStatus: JobStatus): Job
}