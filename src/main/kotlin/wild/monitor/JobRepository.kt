package wild.monitor

interface JobRepository {
    fun newJob(projectKey: String): Job
    fun getJobById(jobId: String): Job
    fun getJobsByProjectKey(projectKey: String): List<Job>
    fun updateJob(jobId: String, updatedJob: Job)
}