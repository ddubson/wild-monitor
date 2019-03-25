package wild.monitor.controllers

data class NewProjectRequest(val projectName: String)

data class JobRequest(val projectKey: String)

data class UpdateJobStatusRequest(val newStatus: String)
