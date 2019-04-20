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
                       val status: JobStatus,
                       val projectKey: String,
                       val expiresOn: String,
                       val createdOn: String) {
    companion object {
        fun fromJob(job: Job): JobResponse =
                JobResponse(jobId = job.jobId.toString(),
                        status = job.status,
                        projectKey = job.project.projectKey,
                        expiresOn = job.expiresOn.format(DateTimeFormatter.ISO_DATE_TIME),
                        createdOn = job.createdOn.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}

data class ErrorResponse(val message: String = "An error has occurred.",
                         val howToRectify: String? = "No specific instructions specified.")