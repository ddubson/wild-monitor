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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import wild.monitor.models.Project
import wild.monitor.repositories.ProjectRepository

@ExtendWith(SpringExtension::class)
@WebMvcTest(GetProjectsController::class)
class GetProjectsControllerTest {
    @TestConfiguration
    class TestConfig {
        @Bean
        fun projectRepository(): ProjectRepository = mockk()
    }

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun getProjects_whenRequested_retrievesAllProjects() {
        val existingProject = Project("My Example Project")
        every { projectRepository.findAll() } returns listOf(existingProject)

        this.mockMvc.perform(MockMvcRequestBuilders.get("/projects"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].id").value(existingProject.id.toString()))
                .andExpect(jsonPath("$[0].projectKey").value(existingProject.projectKey))
                .andExpect(jsonPath("$[0].projectName").value("My Example Project"))
    }
}
