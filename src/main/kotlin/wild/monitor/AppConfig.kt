package wild.monitor

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun projectRepository(): ProjectRepository = InMemoryProjectRepository()

    @Bean
    fun dataStore(): InMemoryDataStore = InMemoryDataStore()
}