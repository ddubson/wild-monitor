package wild.monitor.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.models.Project
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository
import java.util.*

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class JobRepositoryTest {
    @Autowired
    lateinit var testEntityManager: TestEntityManager

    @Autowired
    lateinit var jobRepository: JobRepository

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Test
    fun jobRepository_canFindASavedJobSuccessfully() {
        val project = Project(projectName = "test_project")
        testEntityManager.persist(project)
        val existingProject = projectRepository.findAll()[0]
        val job = Job(UUID.randomUUID(), JobStatus.PENDING, existingProject)
        testEntityManager.persist(job)

        val existingJob = jobRepository.findByJobId(job.jobId)!!
        assertThat(existingJob.dbId).isNotNull()
        assertThat(existingJob.jobId).isEqualTo(job.jobId)
        assertThat(existingJob.createdOn).isNotNull()
        assertThat(existingJob.expiresOn).isNotNull()
        assertThat(existingJob.project).isEqualTo(existingProject)
        assertThat(existingJob.status).isEqualTo(JobStatus.PENDING)
    }

    @Test
    fun jobRepository_canSaveAJobSuccessfully() {
        val project = Project(projectName = "test_project")
        testEntityManager.persist(project)
        val existingProject = projectRepository.findAll()[0]
        val job = Job(UUID.randomUUID(), JobStatus.PENDING, existingProject)
        jobRepository.save(job)

        val existingJob = jobRepository.findByJobId(job.jobId)!!
        assertThat(existingJob.dbId).isNotNull()
        assertThat(existingJob.jobId).isEqualTo(job.jobId)
        assertThat(existingJob.createdOn).isNotNull()
        assertThat(existingJob.expiresOn).isNotNull()
        assertThat(existingJob.project).isEqualTo(existingProject)
        assertThat(existingJob.status).isEqualTo(JobStatus.PENDING)
    }
}