package wild.monitor.usecases

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.models.Project
import wild.monitor.repositories.JobRepository
import java.util.*

internal class DefaultUpdateJobStatusUseCaseTest {
    @Test
    fun updateJobStatus_whenProvidedAValidJobIdAndNextState_savesAndReturnsAnUpdatedJob() {
        val existingProject = mockk<Project>()
        val existingJob = Job(UUID.randomUUID(), JobStatus.PENDING, existingProject)
        val newReturnedJob = Job(existingJob.jobId, JobStatus.STARTED, existingProject)
        val jobRepository = mockk<JobRepository>()
        val updateJobStatusUseCase = DefaultUpdateJobStatusUseCase(jobRepository)

        every {
            jobRepository.findTopByJobIdOrderByUpdatedOnDesc(any())
        } returns existingJob

        every { jobRepository.save(any<Job>()) } returns newReturnedJob

        updateJobStatusUseCase.updateJobStatus(existingJob.jobId.toString(), JobStatus.STARTED)

        verify(atMost = 1) { jobRepository.save(any<Job>()) }
    }
}