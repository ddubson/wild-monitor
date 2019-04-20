package wild.monitor.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import wild.monitor.models.Project
import wild.monitor.repositories.ProjectRepository

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class ProjectRepositoryTest {
    @Autowired
    lateinit var testEntityManager: TestEntityManager

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Test
    fun projectRepository_findAllTest() {
        val project = Project(projectName = "test_project")
        testEntityManager.persist(project)

        val projects = projectRepository.findAll()

        assertThat(projects[0].projectName).isEqualTo("test_project")
        assertThat(projects[0].id).isNotNull()
        assertThat(projects[0].dbId).isNotNull()
        assertThat(projects[0].createdOn).isNotNull()
        assertThat(projects[0].projectKey).isNotNull()
    }

    @Test
    fun projectRepository_saveTest() {
        val project = Project(projectName = "test_project")
        projectRepository.save(project)

        val projects = projectRepository.findAll()

        assertThat(projects[0].projectName).isEqualTo("test_project")
        assertThat(projects[0].id).isNotNull()
        assertThat(projects[0].dbId).isNotNull()
        assertThat(projects[0].createdOn).isNotNull()
        assertThat(projects[0].projectKey).isNotNull()
    }
}