package wild.monitor.controllers

import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.models.Project
import java.time.format.DateTimeFormatter

data class ProjectResponse(val id: String,
                           val projectName: String,
                           val projectKey: String,
                           val createdOn: String) {
    companion object {
        fun fromProject(project: Project): ProjectResponse =
                ProjectResponse(id = project.id.toString(),
                        projectName = project.projectName,
                        projectKey = project.projectKey,
                        createdOn = project.createdOn.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}

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

data class ErrorResponse(val message: String = "An error has occurred.",
                         val howToRectify: String? = "No specific instructions specified.")