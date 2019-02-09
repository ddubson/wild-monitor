package wild.monitor

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/projects")
class GetProjectsController(val projectRepository: ProjectRepository){
    @GetMapping(produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getAllProjects(): ResponseEntity<List<ProjectResponse>>  {
        return ResponseEntity.ok(projectRepository.fetchAll().map { ProjectResponse.fromProject(it) })
    }
}