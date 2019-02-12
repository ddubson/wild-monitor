package wild.monitor.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wild.monitor.repositories.ProjectRepository

@RestController
@RequestMapping("/projects")
class CreateNewProjectController(val projectRepository: ProjectRepository) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun createANewProject(@RequestBody newProjectRequest: NewProjectRequest): ResponseEntity<ProjectResponse> {
        return ResponseEntity.ok(
                ProjectResponse.fromProject(projectRepository.addProject(newProjectRequest.projectName)))
    }
}

data class NewProjectRequest(val projectName: String)
