package wild.monitor.repositories

import wild.monitor.models.Job

interface JobRepository {
    fun newJob(projectKey: String): Job
    fun getJobById(jobId: String): Job
    fun getJobsByProjectKey(projectKey: String): List<Job>
    fun replaceExistingJob(newJob: Job)
}