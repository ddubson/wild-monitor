package wild.monitor.jobs.web

data class JobRequest(val projectKey: String)

data class UpdateJobStatusRequest(val newStatus: String)