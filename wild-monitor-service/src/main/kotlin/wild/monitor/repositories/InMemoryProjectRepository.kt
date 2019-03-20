package wild.monitor.repositories

import wild.monitor.models.Project

class InMemoryProjectRepository : ProjectRepository {
    private val projects: MutableSet<Project> = mutableSetOf()

    override fun fetchAll(): List<Project> {
        return this.projects.toList()
    }

    override fun addProject(projectName: String): Project {
        val element = Project(projectName)
        projects.add(element)
        return element
    }

    override fun existsByProjectKey(projectKey: String): Boolean =
            projects.any { it.projectKey == projectKey }
}