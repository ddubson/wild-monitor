package wild.monitor.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wild.monitor.models.JobStatus
import wild.monitor.usecases.UpdateJobStatusUseCase

@RestController
class UpdateJobStatusController(private val updateJobStatusUseCase: UpdateJobStatusUseCase) {
    @PatchMapping("/jobs/{jobId}",
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun updateJobStatus(@PathVariable("jobId") jobId: String,
                        @RequestBody jobStatusRequest: UpdateJobStatusRequest): ResponseEntity<JobResponse> {
        return ok(
                JobResponse.fromGroupedJobs(
                        listOf(updateJobStatusUseCase.updateJobStatus(jobId,
                                JobStatus.valueOf(jobStatusRequest.newStatus)))))
    }
}