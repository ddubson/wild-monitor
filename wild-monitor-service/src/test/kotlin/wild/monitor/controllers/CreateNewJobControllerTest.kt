package wild.monitor.controllers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import wild.monitor.WildMonitorTester
import wild.monitor.helpers.IsISODateTimeCloseTo.Companion.isISODateTimeCloseTo
import wild.monitor.repositories.InMemoryJobRepository
import wild.monitor.repositories.InMemoryProjectRepository
import wild.monitor.repositories.ProjectRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@WebMvcTest(CreateNewJobController::class)
@Import(InMemoryProjectRepository::class, InMemoryJobRepository::class)
internal class CreateNewJobControllerTest : WildMonitorTester() {
    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun createNewJob_whenProvidedAnExistingProjectKey_createsANewJobWithPendingStatus() {
        withExistingProject(projectRepository, "My Example Project") { projectKey ->
            val jobStatus = "PENDING"
            val requestBody = """
                { "projectKey": "$projectKey" }
            """.trimIndent()

            this.mockMvc.perform(MockMvcRequestBuilders.post("/jobs")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").isNotEmpty)
                    .andExpect(jsonPath("$.status").value(jobStatus))
                    .andExpect(jsonPath("$.projectKey").value(projectKey))
                    .andExpect(jsonPath("$.createdOn")
                            .value(isISODateTimeCloseTo(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))))
        }
    }

    @Test
    fun createNewJob_whenProvidedANonExistentKey_returnsAnInvalidProjectError() {
        val projectKey = "non_existent_key"
        val requestBody = """
            { "projectKey": "$projectKey" }
        """.trimIndent()

        this.mockMvc.perform(MockMvcRequestBuilders.post("/jobs")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Project does not exist."))
    }
}