package wild.monitor.projects.web

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wild.monitor.NoProjectNameSuppliedException
import wild.monitor.ProjectNameTakenException
import wild.monitor.projects.CreateProjectUseCase
import wild.monitor.web_support.ErrorResponse

@RestController
@RequestMapping("/projects")
class CreateNewProjectController(private val createProjectUseCase: CreateProjectUseCase<ProjectResponse>) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun createANewProject(@RequestBody newProjectRequest: NewProjectRequest): ResponseEntity<ProjectResponse> =
            ok(createProjectUseCase.createProject(newProjectRequest.projectName))

    @ExceptionHandler(ProjectNameTakenException::class)
    fun projectNameTaken(e: ProjectNameTakenException): ResponseEntity<ErrorResponse> =
            badRequest().body(ErrorResponse(e.message!!))

    @ExceptionHandler(NoProjectNameSuppliedException::class)
    fun noProjectNameSuppliedException(e: NoProjectNameSuppliedException): ResponseEntity<ErrorResponse> =
            badRequest().body(ErrorResponse(e.message!!, "Please provide a unique project name."))
}
