package wild.monitor.repositories

import wild.monitor.controllers.ProjectDoesNotExistException
import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import java.util.*

class InMemoryJobRepository(val projectRepository: ProjectRepository): JobRepository {
    private val jobs: MutableSet<Job> = mutableSetOf()

    override fun replaceExistingJob(newJob: Job) {
        jobs.remove(jobs.find { newJob.id == it.id })
        jobs.add(newJob)
    }

    override fun getJobsByProjectKey(projectKey: String): List<Job>
            = jobs.filter { it.projectKey == projectKey }

    override fun getJobById(jobId: String): Job =
            jobs.find { it.id.toString() == jobId } ?: throw JobNotFoundException()

    override fun newJob(projectKey: String): Job {
        if (!projectRepository.existsByProjectKey(projectKey)) {
            throw ProjectDoesNotExistException()
        }

        val job = Job(UUID.randomUUID(), JobStatus.PENDING, projectKey)
        jobs.add(job)
        return job
    }
}

class JobNotFoundException: RuntimeException()