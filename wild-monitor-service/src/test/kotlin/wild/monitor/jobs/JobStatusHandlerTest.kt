package wild.monitor.jobs

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import wild.monitor.projects.Project
import java.util.*

class JobStatusHandlerTest {
    @Test
    @DisplayName("Status: Pending -> Started")
    fun progressToNextState_whenProgressingFromPendingToStarted_thenItShouldSucceed() {
        val project = Project("Test Project")
        val job = Job(UUID.randomUUID(), JobStatus.PENDING, project)
        val actualJob: Job = job.progressToNextState(JobStatus.STARTED)
        assertThat(actualJob.jobId).isEqualTo(job.jobId)
        assertThat(actualJob.status).isEqualTo(JobStatus.STARTED)
    }

    @Test
    @DisplayName("Status: Started -> Succeeded")
    fun progressToNextState_whenProgressingFromStartedToSucceeded_thenItShouldSucceed() {
        val project = Project("Test Project")
        val job = Job(UUID.randomUUID(), JobStatus.STARTED, project)
        val actualJob: Job = job.progressToNextState(JobStatus.SUCCEEDED)
        assertThat(actualJob.jobId).isEqualTo(job.jobId)
        assertThat(actualJob.status).isEqualTo(JobStatus.SUCCEEDED)
    }

    @Test
    @DisplayName("Status: Started -> Failed")
    fun progressToNextState_whenProgressingFromStartedToFailed_thenItShouldSucceed() {
        val project = Project("Test Project")
        val job = Job(UUID.randomUUID(), JobStatus.STARTED, project)
        val actualJob: Job = job.progressToNextState(JobStatus.FAILED)
        assertThat(actualJob.jobId).isEqualTo(job.jobId)
        assertThat(actualJob.status).isEqualTo(JobStatus.FAILED)
    }

    @Test
    @DisplayName("Status: Started -> Expired")
    fun progressToNextState_whenProgressingFromStartedToExpired_thenItShouldSucceed() {
        val project = Project("Test Project")
        val job = Job(UUID.randomUUID(), JobStatus.STARTED, project)
        val actualJob: Job = job.progressToNextState(JobStatus.EXPIRED)
        assertThat(actualJob.jobId).isEqualTo(job.jobId)
        assertThat(actualJob.status).isEqualTo(JobStatus.EXPIRED)
    }
}