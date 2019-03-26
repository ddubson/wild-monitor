package wild.monitor.usecases

interface CreateProjectUseCase<T> {
    fun createProject(projectName: String): T
}