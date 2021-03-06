package wild.monitor.jobs.web

import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import wild.monitor.helpers.IsISODateTimeCloseTo.Companion.isISODateTimeCloseTo
import wild.monitor.helpers.anHourFromNow
import wild.monitor.helpers.dateTimeRightNow
import wild.monitor.jobs.DefaultUpdateJobStatusUseCase
import wild.monitor.jobs.Job
import wild.monitor.jobs.JobRepository
import wild.monitor.jobs.JobStatus
import wild.monitor.projects.Project
import wild.monitor.projects.ProjectRepository
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(UpdateJobStatusController::class)
@Import(DefaultUpdateJobStatusUseCase::class)
class UpdateJobStatusControllerTest {
    @TestConfiguration
    class TestConfig {
        @Bean
        fun jobRepository(): JobRepository = mockk()

        @Bean
        fun projectRepository(): ProjectRepository = mockk()
    }

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var jobRepository: JobRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun updateJobStatus_whenNextLogicalStatusIsProvided_thenShouldUpdateTheStatusOfJob() {
        val existingProject = Project("Test Project")
        val jobId = UUID.randomUUID()
        val existingJob = Job(jobId, JobStatus.PENDING, existingProject)
        val newJob = Job(existingJob.jobId, JobStatus.STARTED, existingProject)

        every { projectRepository.findByProjectKey(existingProject.projectKey) } returns existingProject
        every { jobRepository.findTopByJobIdOrderByUpdatedOnDesc(eq(jobId)) } returns existingJob
        every { jobRepository.save(any<Job>()) } returns newJob
        every { jobRepository.findByJobId(jobId) } returns listOf(existingJob, newJob)

        this.mockMvc.perform(patch("/jobs/${existingJob.jobId}")
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content("""{ "newStatus": "STARTED" }"""))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.jobId").value(newJob.jobId.toString()))
                .andExpect(jsonPath("$.projectKey").value(existingProject.projectKey))
                .andExpect(jsonPath("$.expiresOn").value(isISODateTimeCloseTo(anHourFromNow())))
                .andExpect(jsonPath("$.createdOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$.stateLog", hasSize<Any>(equalTo(2))))
                .andExpect(jsonPath("$.stateLog[0].status").value("PENDING"))
                .andExpect(jsonPath("$.stateLog[0].updatedOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$.stateLog[1].status").value("STARTED"))
                .andExpect(jsonPath("$.stateLog[1].updatedOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
    }
}