package wild.monitor.controllers

data class JobRequest(val projectKey: String)

data class UpdateJobStatusRequest(val newStatus: String)
