package wild.monitor.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import wild.monitor.JobNotFoundException
import wild.monitor.repositories.JobRepository
import java.util.*

@RestController
class GetJobByIdController(val jobRepository: JobRepository) {
    @GetMapping("/jobs/{jobId}", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getExistingJob(@PathVariable("jobId") jobId: String): ResponseEntity<JobResponse> =
            ResponseEntity.ok(JobResponse.fromGroupedJobs(listOf(jobRepository.findByJobId(UUID.fromString(jobId))!!)
                    ?: throw JobNotFoundException()))

    @ExceptionHandler(JobNotFoundException::class)
    fun jobNotFound(e: JobNotFoundException): ResponseEntity<JobResponse> = ResponseEntity.notFound().build()
}