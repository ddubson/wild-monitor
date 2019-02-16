package wild.monitor.usecases

import wild.monitor.models.JobStatus
import wild.monitor.progressToNextState
import wild.monitor.repositories.JobRepository

class DefaultUpdateJobStatusUseCase(private val jobRepository: JobRepository): UpdateJobStatusUseCase {
    override fun updateJobStatus(jobId: String, newStatus: JobStatus) {
        val job = jobRepository.getJobById(jobId)
        jobRepository.replaceExistingJob(job.progressToNextState(newStatus))
    }
}