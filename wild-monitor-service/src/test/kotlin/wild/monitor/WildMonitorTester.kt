package wild.monitor

import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.models.Project
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository
import java.util.*

abstract class WildMonitorTester {
    protected fun withExistingProject(projectRepository: ProjectRepository,
                                      projectName: String,
                                      existingProject: (project: Project) -> Unit) {
        val project = projectRepository.save(Project(projectName))
        existingProject(project)
    }

    protected fun withExistingPendingJob(jobRepository: JobRepository, project: Project, existingJob: (jobId: String) -> Unit) {
        val job: Job = jobRepository.save(Job(UUID.randomUUID(), JobStatus.PENDING, project))
        existingJob(job.jobId.toString())
    }
}