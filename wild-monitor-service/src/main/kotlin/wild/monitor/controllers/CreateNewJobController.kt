package wild.monitor.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wild.monitor.ProjectDoesNotExistException
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository

@RestController
@RequestMapping("/jobs")
class CreateNewJobController(val projectRepository: ProjectRepository,
                             val jobRepository: JobRepository) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun createANewJob(@RequestBody newJobRequest: JobRequest): ResponseEntity<JobResponse> =
            ResponseEntity.ok(JobResponse.fromJob(jobRepository.newJob(newJobRequest.projectKey)))

    @ExceptionHandler(ProjectDoesNotExistException::class)
    fun projectDoesNotExistException(e: ProjectDoesNotExistException): ResponseEntity<ErrorResponse> =
            ResponseEntity.badRequest().body(ErrorResponse(e.message
                    ?: "Project does not exist."))

}
