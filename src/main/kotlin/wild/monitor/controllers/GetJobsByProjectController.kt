package wild.monitor.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wild.monitor.repositories.JobRepository

@RestController
class GetJobsByProjectController(val jobRepository: JobRepository) {
    @GetMapping("/jobs", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getJobsByProjectKey(@RequestParam("projectKey") projectKey: String): ResponseEntity<List<JobResponse>> {
        return ResponseEntity.ok(jobRepository.getJobsByProjectKey(projectKey).map { JobResponse.fromJob(it) })
    }
}