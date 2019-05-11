package wild.monitor.jobs

import java.util.*

class DefaultUpdateJobStatusUseCase(private val jobRepository: JobRepository) : UpdateJobStatusUseCase {
    override fun updateJobStatus(jobId: String, newStatus: JobStatus) {
        val job = jobRepository.findTopByJobIdOrderByUpdatedOnDesc(UUID.fromString(jobId))
        job?.let {
            jobRepository.save(it.progressToNextState(newStatus))
        }
    }
}