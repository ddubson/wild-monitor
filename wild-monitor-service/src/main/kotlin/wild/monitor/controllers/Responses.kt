package wild.monitor.controllers

import wild.monitor.models.Job
import wild.monitor.models.JobStatus
import wild.monitor.models.Project
import java.time.format.DateTimeFormatter

data class ProjectResponse(val id: String,
                           val projectName: String,
                           val projectKey: String) {
    companion object {
        fun fromProject(project: Project): ProjectResponse =
                ProjectResponse(id = project.id.toString(),
                        projectName = project.projectName,
                        projectKey = project.projectKey)
    }
}

data class JobResponse(val id: String,
                       val status: JobStatus,
                       val projectKey: String,
                       val createdOn: String) {
    companion object {
        fun fromJob(job: Job): JobResponse =
                JobResponse(job.id.toString(), job.status, job.projectKey,
                        createdOn = job.createdOn.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}

data class NewJobErrorResponse(val message: String)