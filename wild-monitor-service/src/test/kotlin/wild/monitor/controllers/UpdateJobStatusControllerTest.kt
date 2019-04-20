package wild.monitor.controllers

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import wild.monitor.helpers.IsISODateTimeCloseTo
import wild.monitor.helpers.anHourFromNow
import wild.monitor.helpers.dateTimeRightNow
import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.models.Project
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository
import wild.monitor.usecases.DefaultUpdateJobStatusUseCase
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
        val existingJob = Job(UUID.randomUUID(), JobStatus.PENDING, existingProject)
        val newJob = Job(existingJob.jobId, JobStatus.STARTED, existingProject)

        every { projectRepository.findByProjectKey(existingProject.projectKey) } returns existingProject
        every { jobRepository.findByJobId(existingJob.jobId) } returns existingJob
        every { jobRepository.save(any<Job>()) } returns newJob

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/jobs/${existingJob.jobId}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("""{ "newStatus": "STARTED" }"""))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobId").value(newJob.jobId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("STARTED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectKey").value(existingProject.projectKey))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdOn").value(
                        IsISODateTimeCloseTo.isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expiresOn")
                        .value(IsISODateTimeCloseTo.isISODateTimeCloseTo(anHourFromNow())))
    }
}