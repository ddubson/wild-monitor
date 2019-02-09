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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(UpdateJobStatusController::class)
@Import(InMemoryJobRepository::class, InMemoryProjectRepository::class)
class UpdateJobStatusControllerTest: WildMonitorTester() {
    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var jobRepository: JobRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun updateJobStatus_whenNextLogicalStatusIsProvided_thenShouldUpdateTheStatusOfJob() {
        withExistingProject(projectRepository,"My Example Project") { projectKey ->
            withExistingPendingJob(jobRepository, projectKey) { jobId ->
                this.mockMvc.perform(MockMvcRequestBuilders.patch("/jobs/$jobId")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content("""{ "newStatus": "STARTED" }"""))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(jobId))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("STARTED"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.projectKey").value(projectKey))
            }
        }
    }
}