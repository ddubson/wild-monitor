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
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
@WebMvcTest(CreateNewJobController::class)
internal class CreateNewJobControllerTest {
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
    fun createNewJob_whenProvidedAnExistingProjectKey_createsANewJobWithPendingStatus() {
        val existingProject = Project("Test Project")
        val newJob = Job(UUID.randomUUID(), JobStatus.PENDING, existingProject)
        every { projectRepository.findByProjectKey(existingProject.projectKey) } returns existingProject
        every { jobRepository.save(any<Job>()) } returns newJob

        val requestBody = """
                { "projectKey": "${existingProject.projectKey}" }
            """.trimIndent()

        this.mockMvc.perform(post("/jobs")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.jobId").isNotEmpty)
                .andExpect(jsonPath("$.projectKey").value(existingProject.projectKey))
                .andExpect(jsonPath("$.expiresOn").value(isISODateTimeCloseTo(anHourFromNow())))
                .andExpect(jsonPath("$.createdOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
                .andExpect(jsonPath("$.stateLog", hasSize<Any>(equalTo(1))))
                .andExpect(jsonPath("$.stateLog[0].status").value("PENDING"))
                .andExpect(jsonPath("$.stateLog[0].updatedOn").value(isISODateTimeCloseTo(dateTimeRightNow())))
    }

    @Test
    fun createNewJob_whenProvidedANonExistentKey_returnsAnInvalidProjectError() {
        every { projectRepository.findByProjectKey(any()) } returns null

        val projectKey = "non_existent_key"
        val requestBody = """
            { "projectKey": "$projectKey" }
        """.trimIndent()

        this.mockMvc.perform(post("/jobs")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Project does not exist."))
    }
}