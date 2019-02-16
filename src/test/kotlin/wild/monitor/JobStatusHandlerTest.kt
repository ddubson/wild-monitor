package wild.monitor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import java.util.*

class JobStatusHandlerTest {
    @Test
    @DisplayName("Status: Pending -> Started")
    fun progressToNextState_whenProgressingFromPendingToStarted_thenItShouldSucceed() {
        val job = Job(UUID.randomUUID(), JobStatus.PENDING, "projectkey")
        val actualJob: Job = job.progressToNextState(JobStatus.STARTED)
        assertThat(actualJob.id).isEqualTo(job.id)
        assertThat(actualJob.status).isEqualTo(JobStatus.STARTED)
    }

    @Test
    @DisplayName("Status: Started -> Succeeded")
    fun progressToNextState_whenProgressingFromStartedToSucceeded_thenItShouldSucceed() {
        val job = Job(UUID.randomUUID(), JobStatus.STARTED, "projectkey")
        val actualJob: Job = job.progressToNextState(JobStatus.SUCCEEDED)
        assertThat(actualJob.id).isEqualTo(job.id)
        assertThat(actualJob.status).isEqualTo(JobStatus.SUCCEEDED)
    }
}