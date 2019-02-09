package wild.monitor

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

@ExtendWith(SpringExtension::class)
@WebMvcTest(CreateNewJobController::class)
@Import(InMemoryProjectRepository::class)
internal class CreateNewJobControllerTest {
    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun createNewJob_whenProvidedAnExistingProjectKey_createsANewJobWithPendingStatus() {
        withExistingProject("My Example Project") { projectKey ->
            val jobStatus = "PENDING"
            val requestBody = """
                { "projectKey": "$projectKey" }
            """.trimIndent()

            this.mockMvc.perform(MockMvcRequestBuilders.post("/jobs")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").isNotEmpty)
                    .andExpect(jsonPath("$.jobStatus").value(jobStatus))
                    .andExpect(jsonPath("$.projectKey").value(projectKey))
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

    private fun withExistingProject(projectName: String, existingProject: (projectKey: String) -> Unit) {
        val project = this.projectRepository.addProject(projectName)
        existingProject(project.projectKey)
    }
}