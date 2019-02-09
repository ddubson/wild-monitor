package wild.monitor

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateJobStatusController(private val jobRepository: JobRepository) {
    @PatchMapping("/jobs/{jobId}",
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun updateJobStatus(@PathVariable("jobId") jobId: String,
                        @RequestBody jobStatusRequest: UpdateJobStatusRequest): ResponseEntity<JobResponse> {
        val job = jobRepository.getJobById(jobId)
        val updatedJob = job.copy(
                id = job.id,
                status = JobStatus.valueOf(jobStatusRequest.newStatus),
                projectKey = job.projectKey)
        jobRepository.updateJob(job.id.toString(), updatedJob)
        return ResponseEntity.ok(JobResponse.fromJob(updatedJob))
    }
}