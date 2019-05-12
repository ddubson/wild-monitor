package wild.monitor.jobs.web

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wild.monitor.ProjectNotFoundException
import wild.monitor.jobs.Job
import wild.monitor.jobs.JobRepository
import wild.monitor.jobs.JobStatus
import wild.monitor.projects.ProjectRepository
import java.util.*

@RestController
@RequestMapping("/jobs")
class CreateNewJobController(val projectRepository: ProjectRepository,
                             val jobRepository: JobRepository) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_PLAIN_VALUE])
    fun createANewJob(@RequestBody newJobRequest: JobRequest,
                      @RequestParam("justJobId", required = false) justJobId: Boolean = false): ResponseEntity<*> {
        val project = projectRepository.findByProjectKey(newJobRequest.projectKey)
                ?: throw ProjectNotFoundException()

        val newJob = jobRepository.save(Job(UUID.randomUUID(), JobStatus.PENDING, project))
        return if (justJobId) {
            ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(newJob.jobId.toString())
        } else {
            ok(JobResponse.fromGroupedJobs(listOf(newJob)))
        }
    }
}
