package wild.monitor.usecases

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import wild.monitor.ProjectNameTakenException
import wild.monitor.controllers.ProjectResponse
import wild.monitor.repositories.InMemoryProjectRepository
import wild.monitor.repositories.ProjectRepository
import wild.monitor.usecases.web.CreateProjectWebUseCase

class CreateProjectWebUseCaseTest {
    private lateinit var projectRepository: ProjectRepository
    private lateinit var createProjectUseCase: CreateProjectWebUseCase

    @BeforeEach
    fun beforeEach() {
        projectRepository = InMemoryProjectRepository()
        createProjectUseCase = CreateProjectWebUseCase(projectRepository)
    }

    @Test
    fun createProject_whenProvidedAValidProjectName_returnsNewProjectResponse() {
        val projectName = "Project1"
        val projectResponse: ProjectResponse = createProjectUseCase.createProject(projectName)
        assertThat(projectResponse.id).isNotNull()
        assertThat(projectResponse.projectKey).isNotNull()
        assertThat(projectResponse.projectName).isEqualTo(projectName)
    }

    @Test
    fun createProject_whenProvidedADuplicateName_thenThrowsProjectNameTakenException() {
        assertThrows<ProjectNameTakenException> {
            val projectName = "Project1"
            createProjectUseCase.createProject(projectName)
            createProjectUseCase.createProject(projectName)
        }
    }

    @AfterEach
    fun afterEach() {
        projectRepository.clear()
    }
}