package wild.monitor.jobs.web

import wild.monitor.jobs.Job
import wild.monitor.jobs.JobStatus
import java.time.format.DateTimeFormatter

data class JobResponse(val jobId: String,
                       val projectKey: String,
                       val createdOn: String,
                       val expiresOn: String,
                       val stateLog: List<JobStateLogResponse>) {
    companion object {
        fun fromGroupedJobs(jobs: List<Job>): JobResponse =
                JobResponse(jobId = jobs[0].jobId.toString(),
                        projectKey = jobs[0].project.projectKey,
                        createdOn = jobs[0].createdOn.format(DateTimeFormatter.ISO_DATE_TIME),
                        expiresOn = jobs[jobs.lastIndex].expiresOn.format(DateTimeFormatter.ISO_DATE_TIME),
                        stateLog = jobs.map {
                            JobStateLogResponse(
                                    it.status,
                                    it.updatedOn.format(DateTimeFormatter.ISO_DATE_TIME))
                        })
    }
}

data class JobStateLogResponse(val status: JobStatus,
                               val updatedOn: String)