package wild.monitor.controllers

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import wild.monitor.helpers.IsISODateTimeCloseTo.Companion.isISODateTimeCloseTo
import wild.monitor.helpers.anHourFromNow
import wild.monitor.helpers.dateTimeRightNow
import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.models.Project
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(GetJobByIdController::class)
class GetJobByIdControllerTest {
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
    fun getJob_whenProvidedAValidJobId_returnsJobDetails() {
        val existingProject = Project("My Example Project")
        val job = Job(UUID.randomUUID(), JobStatus.PENDING, existingProject)
        every { projectRepository.findByProjectKey(existingProject.projectKey) } returns existingProject
        every { jobRepository.findByJobId(job.jobId) } returns job

        this.mockMvc.perform(get("/jobs/${job.jobId}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.jobId").value(job.jobId.toString()))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.projectKey").value(existingProject.projectKey))
                .andExpect(jsonPath("$.expiresOn")
                        .value(isISODateTimeCloseTo(anHourFromNow())))
                .andExpect(jsonPath("$.createdOn")
                        .value(isISODateTimeCloseTo(dateTimeRightNow())))
    }

    @Test
    fun getJob_whenProvidedNonExistentJobId_returnsJobNotFoundError() {
        val invalidId = UUID.randomUUID()
        every { jobRepository.findByJobId(invalidId) } returns null
        this.mockMvc.perform(get("/jobs/$invalidId"))
                .andExpect(status().isNotFound)
    }
}