package wild.monitor.jobs.web

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wild.monitor.jobs.JobRepository
import wild.monitor.jobs.JobStatus
import wild.monitor.jobs.UpdateJobStatusUseCase
import java.util.*

@RestController
class UpdateJobStatusController(private val updateJobStatusUseCase: UpdateJobStatusUseCase,
                                private val jobRepository: JobRepository) {
    @PatchMapping("/jobs/{jobId}",
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun updateJobStatus(@PathVariable("jobId") jobId: String,
                        @RequestBody jobStatusRequest: UpdateJobStatusRequest): ResponseEntity<JobResponse> {
        updateJobStatusUseCase.updateJobStatus(jobId, JobStatus.valueOf(jobStatusRequest.newStatus))
        return ok(JobResponse.fromGroupedJobs(jobRepository.findByJobId(UUID.fromString(jobId))))
    }
}