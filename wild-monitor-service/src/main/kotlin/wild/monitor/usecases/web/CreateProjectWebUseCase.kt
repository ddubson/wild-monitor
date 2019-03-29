package wild.monitor.usecases.web

import wild.monitor.NoProjectNameSuppliedException
import wild.monitor.ProjectNameTakenException
import wild.monitor.controllers.ProjectResponse
import wild.monitor.repositories.ProjectRepository
import wild.monitor.usecases.CreateProjectUseCase

class CreateProjectWebUseCase(private val projectRepository: ProjectRepository) : CreateProjectUseCase<ProjectResponse> {
    override fun createProject(projectName: String): ProjectResponse {
        if(projectName.isBlank()) {
            throw NoProjectNameSuppliedException()
        }

        if(projectRepository.existsByProjectName(projectName)) {
            throw ProjectNameTakenException()
        }

        val project = projectRepository.addProject(projectName)
        return ProjectResponse.fromProject(project)
    }
}