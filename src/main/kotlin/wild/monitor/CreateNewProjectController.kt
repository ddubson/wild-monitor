package wild.monitor

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/projects")
class CreateNewProjectController(val projectRepository: ProjectRepository) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun createANewProject(@RequestBody newProjectRequest: NewProjectRequest): ResponseEntity<NewProjectResponse> {
        return ResponseEntity.ok(
                NewProjectResponse.fromProject(projectRepository.addProject(newProjectRequest.projectName)))
    }
}

data class NewProjectRequest(val projectName: String)

data class NewProjectResponse(val id: String,
                              val projectName: String,
                              val projectKey: String) {
    companion object {
        fun fromProject(project: Project): NewProjectResponse =
                NewProjectResponse(id = project.id.toString(),
                        projectName = project.projectName,
                        projectKey = project.projectKey)
    }
}