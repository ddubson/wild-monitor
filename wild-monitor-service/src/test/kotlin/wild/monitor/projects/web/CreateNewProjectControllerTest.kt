package wild.monitor.projects.web

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import wild.monitor.helpers.IsISODateTimeCloseTo.Companion.isISODateTimeCloseTo
import wild.monitor.projects.CreateProjectUseCase
import wild.monitor.projects.NoProjectNameSuppliedException
import wild.monitor.projects.Project
import wild.monitor.projects.ProjectNameTakenException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@WebMvcTest(CreateNewProjectController::class)
class CreateNewProjectControllerTest {
    @TestConfiguration
    class TestConfig {
        @Bean
        fun createProjectUseCase(): CreateProjectUseCase<ProjectResponse> = mockk()
    }

    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var createNewProjectUseCase: CreateProjectUseCase<ProjectResponse>

    @BeforeEach
    fun beforeEach(webApplicationContext: WebApplicationContext,
                   restDocumentation: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation))
                .build()
    }

    @Test
    fun createNewProject_whenProvidedAName_shouldReturnProjectDetails() {
        val projectName = "My Project Nickname"
        val newProject = Project(projectName)
        val newProjectResponse = ProjectResponse.fromProject(newProject)

        every { createNewProjectUseCase.createProject(projectName) } returns newProjectResponse

        val requestBody = """
            { "projectName": "$projectName" }
        """.trimIndent()

        this.mockMvc.perform(post("/projects")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(newProject.id.toString()))
                .andExpect(jsonPath("$.projectKey").value(newProject.projectKey))
                .andExpect(jsonPath("$.projectName").value(projectName))
                .andExpect(jsonPath("$.createdOn")
                        .value(isISODateTimeCloseTo(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))))
                .andDo(document("creating-a-project"))
    }

    @Test
    fun createNewProject_whenProvidedADuplicateProjectName_shouldReturnBadRequest() {
        val requestBody = """
            { "projectName": "Duplicate" }
        """.trimIndent()

        every { createNewProjectUseCase.createProject(any()) } throws ProjectNameTakenException()

        this.mockMvc.perform(post("/projects")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Project name has already been taken."))
    }

    @Test
    fun createNewProject_whenProjectNameIsNotSupplied_shouldReturnBadRequest() {
        val requestBody = """
            { "projectName": "" }
        """.trimIndent()

        every { createNewProjectUseCase.createProject("") } throws NoProjectNameSuppliedException()

        this.mockMvc.perform(post("/projects")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Project name was not supplied."))
                .andExpect(jsonPath("$.howToRectify").value("Please provide a unique project name."))
    }
}