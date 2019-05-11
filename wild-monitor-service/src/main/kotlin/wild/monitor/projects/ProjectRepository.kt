package wild.monitor.projects

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: JpaRepository<Project, Int> {
    fun findByProjectKey(projectKey: String): Project?
    fun existsByProjectName(projectName: String): Boolean
}