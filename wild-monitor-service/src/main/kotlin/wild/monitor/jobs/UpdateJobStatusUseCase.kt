package wild.monitor.jobs

interface UpdateJobStatusUseCase {
    fun updateJobStatus(jobId: String, newStatus: JobStatus)
}