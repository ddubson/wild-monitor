package wild.monitor

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