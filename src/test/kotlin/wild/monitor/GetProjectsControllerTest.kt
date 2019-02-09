package wild.monitor

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

@ExtendWith(SpringExtension::class)
@WebMvcTest(GetProjectsController::class)
@Import(InMemoryProjectRepository::class)
class GetProjectsControllerTest: WildMonitorTester() {
    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getProjects_whenRequested_retrievesAllProjects() {
        withExistingProject(projectRepository, "My Example Project") { projectKey ->
            this.mockMvc.perform(MockMvcRequestBuilders.get("/projects"))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$[0].id").isNotEmpty)
                    .andExpect(jsonPath("$[0].projectKey").value(projectKey))
                    .andExpect(jsonPath("$[0].projectName").value("My Example Project"))
        }
    }
}