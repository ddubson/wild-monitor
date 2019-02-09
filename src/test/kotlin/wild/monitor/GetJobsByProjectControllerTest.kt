package wild.monitor

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(GetJobsByProjectController::class)
@Import(InMemoryJobRepository::class, InMemoryProjectRepository::class)
class GetJobsByProjectControllerTest : WildMonitorTester() {
    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var jobRepository: JobRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getJobsByProjectKey_whenProvidedValidProjectKey_returnsArrayOfJobsAssociated() {
        withExistingProject(projectRepository, "My Example Project") { projectKey ->
            withExistingPendingJob(jobRepository, projectKey) { jobId ->
                this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/jobs")
                        .param("projectKey", projectKey))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(jobId))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("PENDING"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].projectKey").value(projectKey))
            }
        }
    }
}