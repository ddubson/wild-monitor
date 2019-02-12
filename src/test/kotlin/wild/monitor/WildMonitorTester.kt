package wild.monitor

import wild.monitor.models.Job
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository

abstract class WildMonitorTester {
    protected fun withExistingProject(projectRepository: ProjectRepository,
                                      projectName: String,
                                      existingProject: (projectKey: String) -> Unit) {
        val project = projectRepository.addProject(projectName)
        existingProject(project.projectKey)
    }

    protected fun withExistingPendingJob(jobRepository: JobRepository, projectKey: String, existingJob: (jobId: String) -> Unit) {
        val job: Job = jobRepository.newJob(projectKey)
        existingJob(job.id.toString())
    }
}