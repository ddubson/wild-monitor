package wild.monitor.repositories

import wild.monitor.models.Project

interface ProjectRepository {
    fun existsByProjectKey(projectKey: String): Boolean
    fun existsByProjectName(projectName: String): Boolean
    fun addProject(projectName: String): Project
    fun fetchAll(): List<Project>
    fun clear()
}