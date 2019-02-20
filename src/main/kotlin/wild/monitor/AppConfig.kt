package wild.monitor

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import wild.monitor.repositories.InMemoryJobRepository
import wild.monitor.repositories.InMemoryProjectRepository
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository
import wild.monitor.usecases.DefaultUpdateJobStatusUseCase
import wild.monitor.usecases.UpdateJobStatusUseCase

@Configuration
@Profile("default", "local", "test", "prod")
class AppConfig {
    @Bean
    fun updateJobStatusUseCase(jobRepository: JobRepository): UpdateJobStatusUseCase =
            DefaultUpdateJobStatusUseCase(jobRepository)

    @Bean
    fun projectRepository(): ProjectRepository = InMemoryProjectRepository()

    @Bean
    fun jobRepository(projectRepository: ProjectRepository, applicationEventPublisher: ApplicationEventPublisher): JobRepository =
            InMemoryJobRepository(projectRepository, applicationEventPublisher)

    @Bean
    fun dataStore(): InMemoryDataStore = InMemoryDataStore()
}