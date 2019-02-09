package wild.monitor

interface ProjectRepository {
    fun existsByProjectKey(projectKey: String): Boolean
    fun addProject(projectName: String): Project
}