package wild.monitor

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import wild.monitor.jobs.JobRepository
import wild.monitor.projects.ProjectRepository
import wild.monitor.projects.CreateProjectUseCase
import wild.monitor.jobs.DefaultUpdateJobStatusUseCase
import wild.monitor.jobs.UpdateJobStatusUseCase
import wild.monitor.projects.web.CreateProjectWebUseCase
import wild.monitor.projects.web.ProjectResponse

@Configuration
@Profile("default", "local", "test", "prod")
class AppConfig {
    @Bean
    fun createProjectUseCase(projectRepository: ProjectRepository): CreateProjectUseCase<ProjectResponse> =
            CreateProjectWebUseCase(projectRepository)

    @Bean
    fun updateJobStatusUseCase(jobRepository: JobRepository): UpdateJobStatusUseCase =
            DefaultUpdateJobStatusUseCase(jobRepository)
}