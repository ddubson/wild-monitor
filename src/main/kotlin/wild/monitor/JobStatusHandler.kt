package wild.monitor

import wild.monitor.models.Job
import wild.monitor.models.JobStatus

fun Job.progressToNextState(nextStatus: JobStatus): Job {
    return when (nextStatus) {
        JobStatus.STARTED -> if (this.status == JobStatus.PENDING)
            copy(id = this.id, status = JobStatus.STARTED, projectKey = this.projectKey) else unknownJobStatus()
        JobStatus.SUCCEEDED -> if (this.status == JobStatus.STARTED)
            copy(id = this.id, status = JobStatus.SUCCEEDED, projectKey = this.projectKey) else unknownJobStatus()
        JobStatus.FAILED -> if(this.status == JobStatus.STARTED)
            copy(id = this.id, status = JobStatus.FAILED, projectKey = this.projectKey) else unknownJobStatus()
        else -> unknownJobStatus()
    }
}

fun unknownJobStatus(): Nothing {
    throw IllegalStateException("Unknown job status.")
}