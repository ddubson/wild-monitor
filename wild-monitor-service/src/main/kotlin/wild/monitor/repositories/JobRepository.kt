package wild.monitor.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wild.monitor.models.Job
import wild.monitor.models.Project
import java.util.*

@Repository
interface JobRepository: JpaRepository<Job, Integer> {
    fun findByJobId(jobId: UUID): Job?
    fun findJobsByProject(project: Project): List<Job>
}