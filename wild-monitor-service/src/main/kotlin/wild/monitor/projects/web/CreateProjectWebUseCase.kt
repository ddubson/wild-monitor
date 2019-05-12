package wild.monitor.projects.web

import wild.monitor.projects.CreateProjectUseCase
import wild.monitor.projects.NoProjectNameSuppliedException
import wild.monitor.projects.Project
import wild.monitor.projects.ProjectNameTakenException
import wild.monitor.projects.ProjectRepository

class CreateProjectWebUseCase(private val projectRepository: ProjectRepository) : CreateProjectUseCase<ProjectResponse> {
    override fun createProject(projectName: String): ProjectResponse {
        if(projectName.isBlank()) {
            throw NoProjectNameSuppliedException()
        }

        if(projectRepository.existsByProjectName(projectName)) {
            throw ProjectNameTakenException()
        }

        val project = projectRepository.save(Project(projectName))
        return ProjectResponse.fromProject(project)
    }
}