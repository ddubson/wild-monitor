package wild.monitor.jobs.web

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wild.monitor.jobs.JobRepository
import wild.monitor.projects.ProjectNotFoundException
import wild.monitor.projects.ProjectRepository

@RestController
class GetJobsByProjectController(val jobRepository: JobRepository,
                                 val projectRepository: ProjectRepository) {
    @GetMapping("/jobs", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getJobsByProjectKey(@RequestParam("projectKey") projectKey: String): ResponseEntity<List<JobResponse>> {
        val project = projectRepository.findByProjectKey(projectKey) ?: throw ProjectNotFoundException()
        val allJobsInProject = jobRepository.findJobsByProject(project)
        val groupedJobs = allJobsInProject.groupBy { it.jobId }
        return ok(groupedJobs.values.map { JobResponse.fromGroupedJobs(it) })
    }
}