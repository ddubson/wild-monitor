package wild.monitor.repositories

import wild.monitor.models.Job
import wild.monitor.models.JobStatus

interface JobRepository {
    fun newJob(projectKey: String): Job
    fun getJobById(jobId: String): Job
    fun getJobsByProjectKey(projectKey: String): List<Job>
    fun updateJobStatus(jobId: String, newStatus: JobStatus)
}