package wild.monitor

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import wild.monitor.repositories.InMemoryJobRepository
import wild.monitor.repositories.InMemoryProjectRepository
import wild.monitor.repositories.JobRepository
import wild.monitor.repositories.ProjectRepository

@Configuration
@Profile("default", "local", "test", "prod")
class AppConfig {
    @Bean
    fun projectRepository(): ProjectRepository = InMemoryProjectRepository()

    @Bean
    fun jobRepository(projectRepository: ProjectRepository): JobRepository = InMemoryJobRepository(projectRepository)

    @Bean
    fun dataStore(): InMemoryDataStore = InMemoryDataStore()
}