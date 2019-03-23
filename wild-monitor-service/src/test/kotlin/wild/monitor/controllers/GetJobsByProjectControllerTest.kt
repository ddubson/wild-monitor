package wild.monitor.controllers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import wild.monitor.WildMonitorTester
import wild.monitor.helpers.IsISODateTimeCloseTo.Companion.isISODateTimeCloseTo
import wild.monitor.repositories.InMemoryJobRepository
import wild.monitor.repositories.InMemoryProjectRepository
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                        .andExpect(status().isOk)
                        .andExpect(jsonPath("$[0].id").value(jobId))
                        .andExpect(jsonPath("$[0].status").value("PENDING"))
                        .andExpect(jsonPath("$[0].projectKey").value(projectKey))
                        .andExpect(jsonPath("$[0].createdOn")
                                .value(isISODateTimeCloseTo(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))))
            }
        }
    }
}