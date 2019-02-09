package wild.monitor

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