package wild.monitor.projects.web

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wild.monitor.projects.ProjectRepository

@RestController
@RequestMapping("/projects")
class GetProjectsController(val projectRepository: ProjectRepository){
    @GetMapping(produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getAllProjects(): ResponseEntity<List<ProjectResponse>>  {
        return ok(projectRepository.findAll().map { ProjectResponse.fromProject(it) })
    }
}