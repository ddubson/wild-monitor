package wild.monitor.repositories

import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.controllers.ProjectDoesNotExistException
import java.util.*

class InMemoryJobRepository(val projectRepository: ProjectRepository): JobRepository {
    private val jobs: MutableSet<Job> = mutableSetOf()

    override fun updateJobStatus(jobId: String, newStatus: JobStatus) {
        val job = getJobById(jobId)
        val updatedJob = job.copy(
                id = job.id,
                status = newStatus,
                projectKey = job.projectKey)
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
        if (!projectRepository.existsByProjectKey(projectKey)) {
            throw ProjectDoesNotExistException()
        }

        val job = Job(UUID.randomUUID(), JobStatus.PENDING, projectKey)
        jobs.add(job)
        return job
    }
}

class JobNotFoundException: RuntimeException()