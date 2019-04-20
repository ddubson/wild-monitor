package wild.monitor.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wild.monitor.models.Project

@Repository
interface ProjectRepository: JpaRepository<Project, Int> {
    fun findByProjectKey(projectKey: String): Project?
    fun existsByProjectName(projectName: String): Boolean
}