package wild.monitor.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wild.monitor.repositories.JobRepository
import wild.monitor.models.JobStatus

@RestController
class UpdateJobStatusController(private val jobRepository: JobRepository) {
    @PatchMapping("/jobs/{jobId}",
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun updateJobStatus(@PathVariable("jobId") jobId: String,
                        @RequestBody jobStatusRequest: UpdateJobStatusRequest): ResponseEntity<JobResponse> {
        jobRepository.updateJobStatus(jobId, JobStatus.valueOf(jobStatusRequest.newStatus))
        return ResponseEntity.ok(JobResponse.fromJob(jobRepository.getJobById(jobId)))
    }
}