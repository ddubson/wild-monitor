package wild.monitor.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import wild.monitor.repositories.JobNotFoundException
import wild.monitor.repositories.JobRepository

@RestController
class GetJobByIdController(val jobRepository: JobRepository) {
    @GetMapping("/jobs/{jobId}", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getExistingJob(@PathVariable("jobId") jobId: String): ResponseEntity<JobResponse> {
        return ResponseEntity.ok(JobResponse.fromJob(jobRepository.getJobById(jobId)))
    }

    @ExceptionHandler(JobNotFoundException::class)
    fun jobNotFound(e: JobNotFoundException): ResponseEntity<JobResponse> {
        return ResponseEntity.notFound().build()
    }
}