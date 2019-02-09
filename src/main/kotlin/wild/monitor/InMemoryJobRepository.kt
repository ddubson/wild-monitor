package wild.monitor

import java.util.*

class InMemoryJobRepository: JobRepository {
    private val jobs: MutableSet<Job> = mutableSetOf()

    override fun updateJob(jobId: String, updatedJob: Job) {
        jobs.remove(jobs.find { jobId == it.id.toString() })
        jobs.add(updatedJob)
    }

    override fun getJobsByProjectKey(projectKey: String): List<Job> {
        return jobs.filter { it.projectKey == projectKey }
    }

    override fun getJobById(jobId: String): Job {
        return jobs.find { it.id.toString() == jobId } ?: throw JobNotFoundException()
    }

    override fun newJob(projectKey: String): Job {
        val job = Job(UUID.randomUUID(), JobStatus.PENDING, projectKey)
        jobs.add(job)
        return job
    }
}

class JobNotFoundException: RuntimeException()