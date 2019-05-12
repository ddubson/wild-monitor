package wild.monitor.projects

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import wild.monitor.projects.web.CreateProjectWebUseCase
import wild.monitor.projects.web.ProjectResponse

class CreateProjectWebUseCaseTest {
    private lateinit var projectRepository: ProjectRepository
    private lateinit var createProjectUseCase: CreateProjectWebUseCase

    @BeforeEach
    fun beforeEach() {
        projectRepository = mockk()
        createProjectUseCase = CreateProjectWebUseCase(projectRepository)
    }

    @Test
    fun createProject_whenProvidedAValidProjectName_returnsNewProjectResponse() {
        val projectName = "Project1"
        val newProject = Project(projectName)
        every {
            projectRepository.save(any<Project>())
        } returns newProject

        every {
            projectRepository.existsByProjectName(projectName)
        } returns false

        val projectResponse: ProjectResponse = createProjectUseCase.createProject(projectName)
        assertThat(projectResponse.id).isEqualTo(newProject.id.toString())
        assertThat(projectResponse.projectKey).isEqualTo(newProject.projectKey)
        assertThat(projectResponse.projectName).isEqualTo(newProject.projectName)
    }

    @Test
    fun createProject_whenProvidedADuplicateName_thenThrowsProjectNameTakenException() {
        val projectName = "Project1"
        every {
            projectRepository.existsByProjectName(projectName)
        } returns true

        assertThrows<ProjectNameTakenException> {
            createProjectUseCase.createProject(projectName)
        }
    }
}