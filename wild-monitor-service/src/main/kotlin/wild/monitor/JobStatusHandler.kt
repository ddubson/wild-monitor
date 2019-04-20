package wild.monitor

import wild.monitor.models.Job
import wild.monitor.models.JobStatus

fun Job.progressToNextState(nextStatus: JobStatus): Job {
    return when (nextStatus) {
        JobStatus.STARTED -> if (this.status == JobStatus.PENDING)
            Job(jobId = this.jobId, status = JobStatus.STARTED, project = this.project) else unknownJobStatus()
        JobStatus.SUCCEEDED
        -> if (this.status == JobStatus.STARTED)
            Job(jobId = this.jobId, status = JobStatus.SUCCEEDED, project = this.project) else unknownJobStatus()
        JobStatus.FAILED -> if(this.status == JobStatus.STARTED)
            Job(jobId = this.jobId, status = JobStatus.FAILED, project = this.project) else unknownJobStatus()
        JobStatus.EXPIRED -> if(this.status == JobStatus.STARTED)
            Job(jobId = this.jobId, status = JobStatus.EXPIRED, project = this.project) else unknownJobStatus()
        else -> unknownJobStatus()
    }
}

fun unknownJobStatus(): Nothing {
    throw IllegalStateException("Unknown job status.")
}