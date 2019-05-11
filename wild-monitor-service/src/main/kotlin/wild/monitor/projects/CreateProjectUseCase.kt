package wild.monitor.projects

interface CreateProjectUseCase<T> {
    fun createProject(projectName: String): T
}