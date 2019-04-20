package wild.monitor.usecases

import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.progressToNextState
import wild.monitor.repositories.JobRepository
import java.util.*

class DefaultUpdateJobStatusUseCase(private val jobRepository: JobRepository): UpdateJobStatusUseCase {
    override fun updateJobStatus(jobId: String, newStatus: JobStatus): Job {
        val job = jobRepository.findTopByJobIdOrderByUpdatedOnDesc(UUID.fromString(jobId))
        return jobRepository.save(job!!.progressToNextState(newStatus))
    }
}