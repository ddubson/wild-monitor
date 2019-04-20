package wild.monitor.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wild.monitor.models.Job
import wild.monitor.models.Project
import java.util.*

@Repository
interface JobRepository: JpaRepository<Job, Int> {
    fun findByJobId(jobId: UUID): Job?
    fun findJobsByProject(project: Project): List<Job>
    fun findTopByJobIdOrderByUpdatedOnDesc(jobId: UUID): Job?
}