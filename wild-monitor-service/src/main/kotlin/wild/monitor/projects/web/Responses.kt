package wild.monitor.projects.web

import wild.monitor.projects.Project
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