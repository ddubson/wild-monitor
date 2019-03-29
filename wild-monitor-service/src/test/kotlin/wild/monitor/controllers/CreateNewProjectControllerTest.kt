package wild.monitor.controllers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import wild.monitor.helpers.IsISODateTimeCloseTo.Companion.isISODateTimeCloseTo
import wild.monitor.repositories.InMemoryProjectRepository
import wild.monitor.usecases.web.CreateProjectWebUseCase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@WebMvcTest(CreateNewProjectController::class)
@Import(CreateProjectWebUseCase::class,
        InMemoryProjectRepository::class)
class CreateNewProjectControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun createNewProject_whenProvidedAName_shouldReturnProjectDetails() {
        val projectName = "My Project Nickname"
        val requestBody = """
            { "projectName": "$projectName" }
        """.trimIndent()

        this.mockMvc.perform(post("/projects")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").isNotEmpty)
                .andExpect(jsonPath("$.projectKey").isNotEmpty)
                .andExpect(jsonPath("$.projectName").value(projectName))
                .andExpect(jsonPath("$.createdOn")
                        .value(isISODateTimeCloseTo(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))))
    }

    @Test
    fun createNewProject_whenProvidedADuplicateProjectName_shouldReturnBadRequest() {
        val requestBody = """
            { "projectName": "Duplicate" }
        """.trimIndent()
        this.mockMvc.perform(post("/projects")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk)
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
        this.mockMvc.perform(post("/projects")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Project name was not supplied."))
                .andExpect(jsonPath("$.howToRectify").value("Please provide a unique project name."))
    }
}