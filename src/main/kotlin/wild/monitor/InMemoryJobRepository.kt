package wild.monitor

import java.util.*

class InMemoryJobRepository: JobRepository {
    private val jobs: MutableSet<Job> = mutableSetOf()

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