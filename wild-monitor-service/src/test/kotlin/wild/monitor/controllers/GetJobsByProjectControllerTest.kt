package wild.monitor.controllers

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
@WebMvcTest(GetJobsByProjectController::class)
class GetJobsByProjectControllerTest {
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
    fun getJobsByProjectKey_whenProvidedValidProjectKey_returnsArrayOfJobsAssociated() {
        val existingProject = Project("My Example Project")

        val job1Id = UUID.randomUUID()
        val job2Id = UUID.randomUUID()

        val job1A = Job(job1Id, JobStatus.PENDING, existingProject)
        val job1B = Job(job1Id, JobStatus.STARTED, existingProject)
        val job1C = Job(job1Id, JobStatus.SUCCEEDED, existingProject)
        val job2A = Job(job2Id, JobStatus.PENDING, existingProject)
        val job2B = Job(job2Id, JobStatus.STARTED, existingProject)

        every { projectRepository.findByProjectKey(existingProject.projectKey) } returns existingProject
        every { jobRepository.findJobsByProject(existingProject) } returns listOf(job1A, job1B, job1C, job2A, job2B)

        this.mockMvc.perform(get("/jobs")
                .param("projectKey", existingProject.projectKey))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].jobId").value(job1A.jobId.toString()))
                .andExpect(jsonPath("$[0].projectKey").value(existingProject.projectKey))
                .andExpect(jsonPath("$[0].createdOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$[0].expiresOn").value(isISODateTimeCloseTo(anHourFromNow())))
                .andExpect(jsonPath("$[0].stateLog", hasSize<Any>(equalTo(3))))
                .andExpect(jsonPath("$[0].stateLog[0].status").value("PENDING"))
                .andExpect(jsonPath("$[0].stateLog[0].updatedOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$[0].stateLog[1].status").value("STARTED"))
                .andExpect(jsonPath("$[0].stateLog[1].updatedOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$[0].stateLog[2].status").value("SUCCEEDED"))
                .andExpect(jsonPath("$[0].stateLog[2].updatedOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$[1].stateLog", hasSize<Any>(equalTo(2))))
                .andExpect(jsonPath("$[1].jobId").value(job2A.jobId.toString()))
                .andExpect(jsonPath("$[1].projectKey").value(existingProject.projectKey))
                .andExpect(jsonPath("$[1].createdOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$[1].expiresOn").value(isISODateTimeCloseTo(anHourFromNow())))
                .andExpect(jsonPath("$[1].stateLog[0].status").value("PENDING"))
                .andExpect(jsonPath("$[1].stateLog[0].updatedOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$[1].stateLog[1].status").value("STARTED"))
                .andExpect(jsonPath("$[1].stateLog[1].updatedOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
    }

    @Test
    fun getJobsByProject_whenProjectDoesNotExist_shouldReturnProjectNotFoundError() {
        val invalidId = UUID.randomUUID().toString()
        every { projectRepository.findByProjectKey(invalidId) } returns null

        this.mockMvc.perform(get("/jobs")
                .param("projectKey", invalidId))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Project does not exist."))
    }
}