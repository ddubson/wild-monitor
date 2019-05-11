package wild.monitor.jobs

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wild.monitor.projects.Project
import java.util.*

@Repository
interface JobRepository: JpaRepository<Job, Int> {
    fun findByJobId(jobId: UUID): List<Job>
    fun findJobsByProject(project: Project): List<Job>
    fun findTopByJobIdOrderByUpdatedOnDesc(jobId: UUID): Job?
}