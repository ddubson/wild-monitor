package wild.monitor

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import wild.monitor.controllers.ProjectResponse
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository
import wild.monitor.usecases.CreateProjectUseCase
import wild.monitor.usecases.DefaultUpdateJobStatusUseCase
import wild.monitor.usecases.UpdateJobStatusUseCase
import wild.monitor.usecases.web.CreateProjectWebUseCase

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