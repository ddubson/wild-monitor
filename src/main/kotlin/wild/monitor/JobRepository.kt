package wild.monitor

interface JobRepository {
    fun newJob(projectKey: String): Job
    fun getJobById(jobId: String): Job
}