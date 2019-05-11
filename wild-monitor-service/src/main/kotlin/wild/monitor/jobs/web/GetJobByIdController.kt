package wild.monitor.jobs.web

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import wild.monitor.jobs.JobRepository
import java.util.*

@RestController
class GetJobByIdController(val jobRepository: JobRepository) {
    @GetMapping("/jobs/{jobId}", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getExistingJob(@PathVariable("jobId") jobId: String): ResponseEntity<JobResponse> =
            jobRepository.findByJobId(UUID.fromString(jobId)).let {
                return ResponseEntity.ok(JobResponse.fromGroupedJobs(it))
            }
}