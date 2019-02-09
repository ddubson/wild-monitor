package wild.monitor

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetJobController(val jobRepository: JobRepository) {
    @GetMapping("/jobs/{jobId}", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getExistingJob(@PathVariable("jobId") jobId: String): ResponseEntity<JobResponse> {
        return ResponseEntity.ok(JobResponse.fromJob(jobRepository.getJobById(jobId)))
    }

    @ExceptionHandler(JobNotFoundException::class)
    fun jobNotFound(e: JobNotFoundException): ResponseEntity<JobResponse> {
        return ResponseEntity.notFound().build()
    }
}

data class JobResponse(val id: String,
                       val status: JobStatus,
                       val projectKey: String) {
    companion object {
        fun fromJob(job: Job): JobResponse = JobResponse(job.id.toString(), job.status, job.projectKey)
    }
}